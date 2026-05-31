package com.TaskManagementTool.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {
	
	private String userOfficialEmail;
	private String password;
	public String getUserOfficialEmail() {
		return userOfficialEmail;
	}
	public void setUserOfficialEmail(String userOfficialEmail) {
		this.userOfficialEmail = userOfficialEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	

}
