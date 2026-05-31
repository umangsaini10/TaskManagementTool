package com.TaskManagementTool.Cloud;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	
	String store(MultipartFile file,String folder);
	byte[] read(String storagePath);
	

}
