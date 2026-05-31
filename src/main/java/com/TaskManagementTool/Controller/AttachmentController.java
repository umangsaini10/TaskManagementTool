package com.TaskManagementTool.Controller;


import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagementTool.Entity.Attachment;
import com.TaskManagementTool.Service.AttachmentService;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
	
	@Autowired
	private AttachmentService attachmentService;
	
	@PostMapping("/upload/{issueId}")
	public ResponseEntity<Attachment>upload(@PathVariable Long issueId,@RequestParam ("file") MultipartFile file,@PathVariable String uploaderBy) {
		return ResponseEntity.ok(attachmentService.upload(issueId, file, uploaderBy));
	}
	
	@GetMapping("/issue/{issueId}")
	public ResponseEntity<List<Attachment>>getFileByIssueId(@PathVariable Long issueId){
		
		return ResponseEntity.ok(attachmentService.getByFileByIssueId(issueId));
		
	}
	
	@GetMapping("/download/stream/{id}")
	public ResponseEntity<Resource> stream(@PathVariable Long id) throws Exception{
		Attachment attachment = attachmentService.getFileById(id);
		URL url = new URL(attachment.getStoragePath());
		InputStream inputStream = url.openStream();
		
		InputStreamResource resource = new InputStreamResource(inputStream);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" +attachment.getFileName()+"\"" )
				.contentType(MediaType.parseMediaType(attachment.getFileContentType())).body(resource);
		
		
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<Void>download(@PathVariable Long id) { 
		
		Attachment attach = attachmentService.getFileById(id);
		return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, attach.getStoragePath()).build();
		
	}

}
