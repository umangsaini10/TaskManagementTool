package com.TaskManagementTool.Security;

import com.TaskManagementTool.TaskManagementToolApplication;
import java.security.Key;
//import java.security.Permission;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.TaskManagementTool.Entity.UserAuth;
import com.TaskManagementTool.Enum.Permission;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	

	private final Key key;
	private final long validateToken=1000L*60*60*12;
	
	public JWTUtil() {
		
		String secret = System.getenv("JWT_SECRET");
		if(secret==null || secret.isEmpty()) {
			secret="Replace this with some secretkey";
			
		}
		key = Keys.hmacShaKeyFor(secret.getBytes());
		
	}
	
	 
	public String generatedToken(UserAuth user) {
		
		Map<String,Object> claim = new HashMap<>();
		claim.put("role",user.getRole().name());
		
		Set<Permission> perm = RolePermissionConfig.getRoleBasedPermission().get(user.getRole());
//		Not Necessary 
//		List<String> permName = perm==null ? List.class:perm.stream().map(Enum::name).collect(Collectors.toList());
		
		Date now = new Date();
		Date expire = new Date(now.getTime()+validateToken);
		
		return Jwts.builder().setClaims(claim).setSubject(user.getUserOfficialEmail()).setIssuedAt(now).setExpiration(expire).signWith(key,SignatureAlgorithm.HS256).compact();
		
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}
		catch(JwtException e) {
			return false;
			
		}
		
	}
	
	public Claims getClaim(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		
	}
	public String getUserEmail(String token) {
		return getClaim(token).getSubject();
	}
	
	public String extractToken(String header) {
		if(header !=null && header.startsWith("Barear ")) {
			return header.substring(7);
		}
		return null;
	}

}
