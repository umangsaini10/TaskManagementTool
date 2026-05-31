package com.TaskManagementTool.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagementTool.Entity.Issue;
import com.TaskManagementTool.Entity.IssueComment;
import com.TaskManagementTool.Entity.Sprint;
import com.TaskManagementTool.Enum.IssuePriority;
import com.TaskManagementTool.Enum.IssueStatus;
import com.TaskManagementTool.Enum.IssueType;
import com.TaskManagementTool.Enum.SprintState;
import com.TaskManagementTool.Repository.IssueCommentRepository;
import com.TaskManagementTool.Repository.IssueRepository;
import com.TaskManagementTool.Repository.SprintRepository;

import jakarta.transaction.Transactional;

@Service
public class IssueService {
	
	@Autowired
	private IssueRepository issueRepo;
	
	@Autowired
	private IssueCommentRepository issueCommentRepo;
	
	@Autowired
	private SprintRepository sprintRepo;
	
	private String generatedKey(Long id) {
		return "PROJ-"+id;
	}
	
	@Transactional
	public Issue createIssue(Issue issue) {
		
		issue.setIssueType(issue.getIssueType()!=null? issue.getIssueType():IssueType.TASK);
		issue.setPriority(issue.getPriority()!=null?issue.getPriority():IssuePriority.MEDIUM);
		issue.setIssueStatus(IssueStatus.OPEN);
		
		Issue saved = issueRepo.save(issue);
		saved.setIssueKey(generatedKey(saved.getId()));
		return issueRepo.save(saved);
	}
	

	public List<Issue>findIssueByAssigneeEmail(String userOfficialEmail){
		return issueRepo.findByAssigneeEmail(userOfficialEmail);
	}
	
	
	public Issue findIssueById(Long id) {
		
		return issueRepo.findById(id).orElseThrow(()-> new RuntimeException("Issue notfound") );
	}
	
	public List<Issue>findIssueBySprint(Long sprintId){
		
		return issueRepo.findBySprintId(sprintId);
	}
	

	@Transactional
	public Issue updateIssueStatus(Long id,IssueStatus issueStatus) {
		
		Issue issue= findIssueById(id);
		
//		IssueStatus newIssueStatus;
		
		if(issueStatus==null ) {
			throw new RuntimeException("Status can not be null");
		}
		
		
		issue.setIssueStatus(issueStatus);
		return issueRepo.save(issue);
		
	}
	
	
	@Transactional
	public IssueComment addComment(Long issueId,String authorEmail,String body) {
		
		
		Issue issue= findIssueById(issueId);
		
		IssueComment comment= new IssueComment();
		
		comment.setIssueId(issue.getId());
		comment.setAuthorEmail(authorEmail);
		comment.setBody(body);
		
		return issueCommentRepo.save(comment);
	}
	
	
	@Transactional
	public Sprint createSprint(Sprint sprint) {
		if(sprint.getSprintstate()==null) {
			sprint.setSprintstate(SprintState.PLANNED);
		}
		return sprintRepo.save(sprint);
	}
	
	public List<Issue>getIssueBySprint(Long sprintId){
		return issueRepo.findBySprintId(sprintId);
	}
	
	
	public List<Issue>search(Map<String,String>filters){
		
		List<Issue>issues= issueRepo.findAll();
		if(filters.containsKey("assigneeEmail")) {
			String email= filters.get("assigneeEmail");
			issues=issues.stream().filter(i-> email.equalsIgnoreCase(i.getAssigneeEmail())).collect(Collectors.toList());
//			return getIssueByAssigneeEmail(filters.get("assigneeEmail"));
		}
		
		
		if(filters.containsKey("IssueStatus")) {
			try {
				IssueStatus status= IssueStatus.valueOf(filters.get("issueStatus"));
				return issueRepo.findByIssueStatus(status);
			} catch (Exception e) {
				throw new RuntimeException("invalid status in filter");
			}
		}
		
		if(filters.containsKey("sprint")) {
			Long sprintId= Long.valueOf(filters.get("sprint"));
			return getIssueBySprint(sprintId);
		}
		
		return issueRepo.findAll();
	}


}
