package com.TaskManagementTool.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskManagementTool.DTO.AuthResponseDTO;
import com.TaskManagementTool.DTO.LoginRequestDTO;
import com.TaskManagementTool.DTO.RegisterRequestDTO;
import com.TaskManagementTool.Entity.UserAuth;
import com.TaskManagementTool.Repository.UserAuthRepository;
import com.TaskManagementTool.Security.JWTUtil;
import com.TaskManagementTool.Security.TokenBlockService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserAuthService {
	
	@Autowired
	private UserAuthRepository userRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailLogService emailService;
	
	@Autowired
	private TokenBlockService tokenBlock;
	
	public AuthResponseDTO register(RegisterRequestDTO register) {
		
		Optional<UserAuth> exist = userRepo.findByUserOfficialEmail(register.getUserOfficialEmail());
		if(exist.isPresent()) {
			throw new RuntimeException("User already Exist");
		}
		
		UserAuth user = new UserAuth();
		user.setUserName(register.getUserName());
		user.setUserOfficialEmail(register.getUserOfficialEmail());
		user.setPassword(passwordEncoder.encode(register.getPassword()));
		user.setRole(register.getRole());
		
		userRepo.save(user);
		
		String token = jwtUtil.generatedToken(user);
		return new AuthResponseDTO(token,"user register successfully");
		
		
	}
	
	public String login(LoginRequestDTO login) {
		
		UserAuth user = userRepo.findByUserOfficialEmail(login.getUserOfficialEmail())
				                                         .orElseThrow(()-> new RuntimeException("User not Found"));
		
		if(!passwordEncoder.matches(login.getPassword(), user.getPassword() )) {
			throw new RuntimeException("Invalid credential");
			
		}
		return jwtUtil.generatedToken(user);
		
	}
	
	
	public void forgotPassword(String email) {
		
		UserAuth user = userRepo.findByUserOfficialEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
		
		String token = UUID.randomUUID().toString();
		
		user.setResetToken(token);
		user.setResetTokenExpire(new Date(System.currentTimeMillis()+10*60*1000));
		
		userRepo.save(user);
		
		emailService.sentResetPasswordEmail(email, token);
		
	}
	
	
	public void resetPassword(String token, String newPassword) {
		
		UserAuth user = userRepo.findByResetToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
		
		if(user.getResetTokenExpire().before(new Date())) {
			throw new RuntimeException("Token got expired");
			
		}
		
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetToken(null);
		user.setResetTokenExpire(null);
		
		userRepo.save(user);
		
		
	}
	
	public String logout(HttpServletRequest request ) {
		String header = request.getHeader("Authorization");
		String token = jwtUtil.extractToken(header);
		
		if(token!=null) {
			tokenBlock.blockListToken(token);
		}
		
		return "Logged Out Successfully ";
	}
 
}
