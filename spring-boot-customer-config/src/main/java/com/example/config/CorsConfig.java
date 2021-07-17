package com.example.config;

import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import org.springframework.web.servlet.config.annotation.*;

import java.util.*;


/**
 * 配置跨域请求
 *
 * @author shizeying
 * @date 2021/06/10
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	static final String[] ORIGINS = new String[]{"POST", "GET", "PUT", "OPTIONS", "DELETE"};
	

	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 允许跨域访问的路径
		registry.addMapping("/**")
		        // 允许跨域访问的源
		        .allowedOrigins("*")
		        // 允许请求方法
		        .allowedMethods(ORIGINS)
		        // 预检间隔时间
		        .maxAge(168000)
		        // 允许头部设置
		        .allowedHeaders("*")
		        // 是否发送cookie
		        .allowCredentials(true);
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		
		configuration.setExposedHeaders(Collections.singletonList("Content-Disposition"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
}
