package com.example.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 配置管理的属性
 *
 * @author shizeying
 * @date 2021/01/04
 */
@Configuration
@Component
@ConfigurationProperties(value = "config.manager")
@Builder
@Data
public class ConfigManageProperties {
	private String extDictPath;
	private String stopDictPath;
	private String remoteDictLocation=null;
	
	public ConfigManageProperties(String extDictPath, String stopDictPath, String remoteDictLocation) {
		this.extDictPath = extDictPath;
		this.stopDictPath = stopDictPath;
		this.remoteDictLocation = remoteDictLocation;
	}
	
	public String getRemoteDictLocation() {
		return remoteDictLocation;
	}
	
	public void setRemoteDictLocation(String remoteDictLocation) {
		this.remoteDictLocation = remoteDictLocation;
	}
	
	public ConfigManageProperties() {
	}
	
	public String getStopDictPath() {
		return stopDictPath;
	}
	
	public void setStopDictPath(String stopDictPath) {
		this.stopDictPath = stopDictPath;
	}
	
	public String getExtDictPath() {
		return extDictPath;
	}
	
	public void setExtDictPath(String extDictPath) {
		this.extDictPath = extDictPath;
	}
	
	
}
