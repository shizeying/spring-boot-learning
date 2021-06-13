package com.example.aop;

import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.*;
import org.springframework.web.client.*;

import java.nio.charset.*;

/**
 * @author shizeying
 * @version 1.0.0
 * @doc
 * @desc {@link RestTemplateConfig}  restTemplate请求配置
 * @date 2020/11/04
 */
@SuppressWarnings("ALL")
@Configuration
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		
		restTemplate.getMessageConverters()
				.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		
		return restTemplate;
	}
	
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		
		factory.setConnectTimeout(15000);
		factory.setReadTimeout(5000);
		
		return factory;
	}
}