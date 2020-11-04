package com.example.config;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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