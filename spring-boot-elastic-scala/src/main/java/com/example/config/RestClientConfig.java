package com.example.config;

import com.sksamuel.elastic4s.ElasticClient;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * elasticsearch属性
 *
 * @author shizeying
 * @date 2021/01/13
 */
@Configuration
@Slf4j
public class RestClientConfig {

	@Autowired
	private ElasticsearchRestClientProperties properties;

	@Bean
	public ElasticClient restClientInit() {
		Objects.requireNonNull(properties.getUris(), "使用es请配置uri");
		//final String uris = properties.getUris().stream().collect(Collectors.joining(","));
		String uris = "http://192.169.4.245:9200,http://192.169.4.245:9201,http://192.169.4.245:9202";
		
		final String username= Optional.ofNullable(properties.getUsername()).orElse("");
		final String password= Optional.ofNullable(properties.getPassword()).orElse("");
		return EsUtils.getClient(uris,username, password);

	}
}
