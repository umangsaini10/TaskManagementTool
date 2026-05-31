package com.TaskManagementTool.Entity;

import java.time.LocalDateTime;

import com.TaskManagementTool.Enum.SprintState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="sprints")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sprint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String sprintName;
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	@Enumerated(EnumType.STRING)
	private SprintState sprintState;
	
	@Column(length=5000)
	private String goal;
	
	private LocalDateTime creaedat= LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public SprintState getSprintstate() {
		return sprintState;
	}

	public void setSprintstate(SprintState sprintState) {
		this.sprintState = sprintState;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public LocalDateTime getCreaedat() {
		return creaedat;
	}

	public void setCreaedat(LocalDateTime creaedat) {
		this.creaedat = creaedat;
	}
	
	


}
