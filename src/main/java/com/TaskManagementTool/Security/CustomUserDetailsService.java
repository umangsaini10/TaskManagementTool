package com.TaskManagementTool.Security;

import java.util.List;
//import java.security.Permission;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.TaskManagementTool.Entity.UserAuth;
import com.TaskManagementTool.Enum.Permission;
import com.TaskManagementTool.Repository.UserAuthRepository;

@Service
public abstract class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserAuthRepository userRepo;
	
	public UserDetails loadUserByEmail(String userOfficialEmail) throws Exception {
		
		UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail).orElseThrow(() -> new RuntimeException("user not found"));
		
		Set<Permission> perms = RolePermissionConfig.getRoleBasedPermission().get(user.getRole());
//		List<GrantedAuthority> authorities = (perms == null? List.class:perms.stream().map(p-> new SimpleGrantedAuthority(p.name())).collect(Collectors.toList()));
		
		return new org.springframework.security.core.userdetails.User(user.getUserOfficialEmail(), user.getPassword(),null);
		
	}

}
