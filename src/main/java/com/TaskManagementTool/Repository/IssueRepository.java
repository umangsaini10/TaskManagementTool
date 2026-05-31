package com.TaskManagementTool.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskManagementTool.Entity.Issue;
import com.TaskManagementTool.Enum.IssueStatus;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
	
	Optional<Issue> findByIssueKey(String issueKey);
	List<Issue> findByAssigneeEmail(String assigneeEmail);
	List<Issue> findByIssueStatus(IssueStatus issueStatus);
	List<Issue> findBySprintId(Long sprintId);
}
