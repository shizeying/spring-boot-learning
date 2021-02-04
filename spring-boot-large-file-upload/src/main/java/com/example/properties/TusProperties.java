package com.example.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@ConfigurationProperties(prefix = "large.file")
@Component
@Primary
public class TusProperties {
	
	private String tusUploadDirectory="classpath:tus/upload";
	
	private String appUploadDirectory="classpath:app/upload";
	
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
	
	public TusProperties() {
	}
	
	public TusProperties(String tusUploadDirectory, String appUploadDirectory) {
		this.tusUploadDirectory = tusUploadDirectory;
		this.appUploadDirectory = appUploadDirectory;
	}
}
