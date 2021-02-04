package com.example.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Administrator
 */
@ConfigurationProperties(prefix = "large.file")
public class TusProperties {
	
	private String tusUploadDirectory = "classpath:tus/upload";
	
	private String appUploadDirectory = "classpath:app/upload";
	
	public TusProperties() {
	}
	
	public TusProperties(String tusUploadDirectory, String appUploadDirectory) {
		this.tusUploadDirectory = tusUploadDirectory;
		this.appUploadDirectory = appUploadDirectory;
	}
	
	public String getTusUploadDirectory() {
		return tusUploadDirectory;
	}
	
	public void setTusUploadDirectory(String tusUploadDirectory) {
		this.tusUploadDirectory = tusUploadDirectory;
	}
	
	public String getAppUploadDirectory() {
		return appUploadDirectory;
	}
	
	public void setAppUploadDirectory(String appUploadDirectory) {
		this.appUploadDirectory = appUploadDirectory;
	}
}
