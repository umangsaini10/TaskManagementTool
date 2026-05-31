package com.TaskManagementTool.DTO;

import com.TaskManagementTool.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Data Tranfer Object (DTO)
public class RegisterRequestDTO {
	
	private String userName;
	private String UserOfficialEmail;
	private String password;
	private Role role;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserOfficialEmail() {
		return UserOfficialEmail;
	}
	public void setUserOfficialEmail(String userOfficialEmail) {
		UserOfficialEmail = userOfficialEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
	

}
