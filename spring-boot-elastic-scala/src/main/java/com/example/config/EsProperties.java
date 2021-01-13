package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * elasticsearch属性
 *
 * @author shizeying
 * @date 2021/01/13
 */
@Configuration
@Component
@ConfigurationProperties(value = "elastic.manager")

public class EsProperties {
	private String uris;
	
	private String password;
	private String username;
	
	
}
