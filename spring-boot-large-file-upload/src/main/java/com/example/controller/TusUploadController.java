package com.example.controller;

import com.example.properties.TusProperties;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Administrator
 */
@Controller
@CrossOrigin(exposedHeaders = {"Location", "Upload-Offset"})
@RequestMapping(value = "/api/")
public class TusUploadController {
	private final TusFileUploadService tusFileUploadService;
	private final Path uploadDirectory;
	private final Path tusUploadDirectory;

	
	public TusUploadController(TusFileUploadService tusFileUploadService,
	                           TusProperties appProperties) {
		this.tusFileUploadService = tusFileUploadService;
		this.uploadDirectory = Paths.get(appProperties.getAppUploadDirectory());
		try {
			Files.createDirectories(this.uploadDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.tusUploadDirectory = Paths.get(appProperties.getTusUploadDirectory());
	}
	
	@RequestMapping(value = {"/upload", "/upload/**"}, method = {RequestMethod.POST,
			RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.GET})
	public void upload(HttpServletRequest servletRequest,
	                   HttpServletResponse servletResponse) throws IOException {
		this.tusFileUploadService.withUploadURI("/api/upload");
		this.tusFileUploadService.process(servletRequest, servletResponse);
		
		String uploadUri = servletRequest.getRequestURI();
		
		Path locksDir = this.tusUploadDirectory.resolve("locks");
		if (Files.exists(locksDir)) {
			try {
				this.tusFileUploadService.cleanup();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		UploadInfo uploadInfo = null;
		try {
			uploadInfo = this.tusFileUploadService.getUploadInfo(uploadUri);
			
		} catch (IOException | TusException e) {
			e.printStackTrace();
		}
		
		if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
			try (InputStream is = this.tusFileUploadService.getUploadedBytes(uploadUri)) {
				Path output = this.uploadDirectory.resolve(uploadInfo.getFileName());
				Files.copy(is, output, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException | TusException e) {
				e.printStackTrace();
			}
			
			try {
				this.tusFileUploadService.deleteUpload(uploadUri);
			} catch (IOException | TusException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Scheduled(fixedDelayString = "PT24H")
	private void cleanup() {
		Path locksDir = this.tusUploadDirectory.resolve("locks");
		if (Files.exists(locksDir)) {
			try {
				this.tusFileUploadService.cleanup();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
