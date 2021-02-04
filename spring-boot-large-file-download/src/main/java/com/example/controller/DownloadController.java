package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@RestController

public class DownloadController {
	 @GetMapping("download/{filePath}")
	public ResponseEntity<Resource> download(@PathVariable("filePath")String filePath, HttpServletRequest request) throws IOException {
		
		
		 Resource resource = loadFileAsResource(filePath);
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
	public Resource loadFileAsResource(String fileName) throws MalformedURLException {
		Path fileStorageLocation = Paths.get("/Users/shizeying/Desktop/")
		                                .toAbsolutePath().normalize();
		Path filePath = fileStorageLocation.resolve(fileName).normalize();
		Resource resource = new UrlResource(filePath.toUri());
		if (resource.exists()) {
			return resource;
		}
		return null;
	}
}
