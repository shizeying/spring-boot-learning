package com.example.controller;

import com.example.properties.TusProperties;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.download.DownloadGetRequestHandler;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import me.desair.tus.server.util.TusServletRequest;
import me.desair.tus.server.util.TusServletResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@CrossOrigin(exposedHeaders = { "Location", "Upload-Offset" })
@RequestMapping(value = "/api/")
public class TusUploadController {
	private final TusFileUploadService tusFileUploadService;
	private DownloadGetRequestHandler handler;
	private final Path uploadDirectory;
	
	private final Path tusUploadDirectory;
	
	public TusUploadController(TusFileUploadService tusFileUploadService,
	                           TusProperties appProperties) {
		this.tusFileUploadService = tusFileUploadService;
		this.tusFileUploadService.withUploadURI("/api/upload");
		this.uploadDirectory = Paths.get(appProperties.getAppUploadDirectory());
		try {
			Files.createDirectories(this.uploadDirectory);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		this.tusUploadDirectory = Paths.get(appProperties.getTusUploadDirectory());
	}
	
	@RequestMapping(value = { "/upload", "/upload/**" }, method = { RequestMethod.POST,
			RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.GET })
	public void upload(HttpServletRequest servletRequest,
	                   HttpServletResponse servletResponse) throws IOException {
		this.tusFileUploadService.process(servletRequest, servletResponse);
		
		String uploadURI = servletRequest.getRequestURI();
		Path locksDir = this.tusUploadDirectory.resolve("locks");
		if (Files.exists(locksDir)) {
			try {
				this.tusFileUploadService.cleanup();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		UploadInfo uploadInfo = null;
		try {
			uploadInfo = this.tusFileUploadService.getUploadInfo(uploadURI);
		}
		catch (IOException | TusException e) {
			e.printStackTrace();
		}
		
		if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
			try (InputStream is = this.tusFileUploadService.getUploadedBytes(uploadURI)) {
				Path output = this.uploadDirectory.resolve(uploadInfo.getFileName());
				Files.copy(is, output, StandardCopyOption.REPLACE_EXISTING);
			}
			catch (IOException | TusException e) {
				e.printStackTrace();
			}
			
			try {
				this.tusFileUploadService.deleteUpload(uploadURI);
			}
			catch (IOException | TusException e) {
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
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@GetMapping(value = { "/download", "/download/**" })
	public void download(HttpServletRequest servletRequest,
	                     HttpServletResponse servletResponse) throws IOException {
		handler.process(new TusServletRequest(servletRequest),
				new TusServletResponse(servletResponse), tusFileUploadService);
		
		
		
		
	}
}
