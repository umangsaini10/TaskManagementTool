package com.TaskManagementTool.Cloud;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService{
	
	

	private Path baseDirs;
	
	public StorageServiceImpl(org.springframework.core.env.Environment env) {
		
		String dir = env.getProperty("attachment.storage.local.base_dir","/tmp/attachments");
		this.baseDirs=Paths.get(dir).toAbsolutePath();
		
		try {
			Files.createDirectories(baseDirs);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public String store(MultipartFile file, String folder) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String ext = "";
		int i = fileName.lastIndexOf(".");
		
		if(i>=0) ext=fileName.substring(i);
		
		String key = folder+"/"+UUID.randomUUID()+ext;
		Path target = baseDirs.resolve(key);
		
		try {
			Files.createDirectories(target,null);
			Files.copy(file.getInputStream(), target,StandardCopyOption.REPLACE_EXISTING);
			return target.toString();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to store the file");
		}
			
	}
	

	public byte[]read(String storagePath) {
		try {
			return Files.readAllBytes(Paths.get(storagePath));
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

}
