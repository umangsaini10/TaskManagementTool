package com.TaskManagementTool.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagementTool.Entity.Attachment;
import com.TaskManagementTool.Repository.AttachmentRepository;
import com.cloudinary.Cloudinary;

@Service
public class AttachmentService {
	
	@Autowired
	private AttachmentRepository attachmentRepo;
	
	@Autowired
	private Cloudinary cloudinary;
	
	public Attachment upload(Long issueId,MultipartFile file,String uploadBy) {
		validateFile(file);
		try {
			
			Map<String, Object>uploadOption=new HashMap<>();
			uploadOption.put("resource_type", "auto");
			
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadOption);
			
			Attachment att = new Attachment();
			att.setIssueId(issueId);
			att.setFileName(file.getOriginalFilename());
			att.setFileContentType(file.getContentType());
			att.setSizeBytes(file.getSize());
			att.setCloudinaryId(uploadResult.get("cloudinary_id").toString());
			att.setStoragePath(uploadResult.get("cloudinary_id").toString());
			att.setUploadedBy(uploadBy);
			
			return attachmentRepo.save(att);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("/file upload failed");
			
		}
		
	}
	
	public List<Attachment>getFileByIssueId(Long issueId) {
		
		return attachmentRepo.findByIssueId(issueId);
	}
	
public List<Attachment>getByFileByIssueId(Long issueId){
		
		
		return attachmentRepo.findByIssueId(issueId);
	}
	
	public Attachment getFileById(Long id) {
		return attachmentRepo.findById(id).orElseThrow(()-> new RuntimeException("File not found"));
	}
	
	public void delete(Long id) {
		
		Attachment attach = getFileById(id);
		
		try {
			Map<String,Object> option = new HashMap<>();
			option.put("resource_type", "auto");
			
			cloudinary.uploader().destroy(attach.getCloudinaryId(), option);
			attachmentRepo.delete(attach);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("delete failed",e);
		}
		
		
		
		
		
	}
	
	private void validateFile(MultipartFile file) {
		
		if(file.isEmpty()) {
			throw new RuntimeException("file cannot be empty !");
		}
		
		long MAX =5*1024*1024;
		
		if(file.getSize()>MAX) {
			throw new RuntimeException("Max file size is 5 MB");
		}
		
		List<String> allowed = Arrays.asList("image/png","image/jpeg","application/pdf","text/plain","text/doc");
		
		if(!allowed.contains(file.getContentType())) {
			
			
			throw new RuntimeException("Invalid file format");
		}
		
	}
	
	

}
