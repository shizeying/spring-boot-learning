package com.example.controller;


import com.example.servcie.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController

public class DownloadController {
	
	@Autowired
	private DownloadService downloadService;
	
	@GetMapping("download/{filePath}")
	public ResponseEntity<Resource> downloadGet(@PathVariable("filePath") String filePath, HttpServletRequest request) {
		
		
		Resource resource = downloadService.loadFileAsResource(filePath);
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			//logger.info("Could not determine file type.");
		}
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok()
		                     .contentType(MediaType.parseMediaType(contentType))
		                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                     .body(resource);
		
		
	}
	
	
	@PostMapping("download")
	public ResponseEntity<Resource> downloadPost(@RequestBody String filePath, HttpServletRequest request) {
		Resource resource = downloadService.loadFileAsResource(filePath);
		// Try to determine file's content type
		String contentType = "application/octet-stream";
		;
		
		return ResponseEntity.ok()
		                     .contentType(MediaType.parseMediaType(contentType))
		                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                     .body(resource);
		
	}
	
	@PostMapping("download/list")
	public ResponseEntity<List<Resource>> downloadPostList(@RequestBody List<String> filePaths, HttpServletRequest request) {
		List<Resource> resource = downloadService.loadFileAsResource(filePaths);
		// Try to determine file's content type
		String contentType = "application/octet-stream";
		
		String[] headerValues = resource.stream().filter(Objects::nonNull)
		                                .map(Resource::getFilename)
		                                .map(a -> "attachment; filename=\"" + a + "\"")
		                                .toArray(String[]::new);
		return ResponseEntity.ok()
		                     .contentType(MediaType.parseMediaType(contentType))
		                     .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
		                     .body(resource);
		
	}
	
	
}
