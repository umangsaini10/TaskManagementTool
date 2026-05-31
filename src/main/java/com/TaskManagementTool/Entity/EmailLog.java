package com.TaskManagementTool.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EmailLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String recipientEmail;
	private String subject;
	@Column(length=5000)
	private String body;
	
	private boolean sentStatus;
	private LocalDateTime sentAt = LocalDateTime.now();
	
	public EmailLog(String recipientEmail,String subject,String body,boolean sentStatus) {
		
		this.recipientEmail=recipientEmail;
		this.subject=subject;
		this.body=body;
		this.sentStatus=sentStatus;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(boolean sentStatus) {
		this.sentStatus = sentStatus;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
	
	
	
	

}
