package com.TaskManagementTool.Entity;

import java.util.Date;

import com.TaskManagementTool.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_auth")



@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAuth {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long id;
	@Column(nullable=false)
	
	private String userName;
	@Column(nullable=false,unique=true)
	
	private String userOfficialEmail;
	@Column(nullable=false)
	
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String resetToken;
	private Date resetTokenExpire;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	public Date getResetTokenExpire() {
		return resetTokenExpire;
	}
	public void setResetTokenExpire(Date resetTokenExpire) {
		this.resetTokenExpire = resetTokenExpire;
	}
	
	
	
	
	
	

}
