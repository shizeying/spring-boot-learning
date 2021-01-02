package com.example.elasticsearch.config;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;

/** ElasticSearch配置文件 Es7使用RestHighLevelClient操作ES */
@Slf4j
@SuppressWarnings("ALL")
@Component
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {
	
	@Autowired
	private ElasticsearchRestClientProperties properties;
	
	/** @return {@link RestHighLevelClient} */
	@Override
	@ConditionalOnMissingBean(RestClientBuilder.class)
	@Autowired
	public RestHighLevelClient elasticsearchClient() {
		
		return Try.of(() -> RestClients.create(clientConfiguration()).rest())
		          .onFailure(error -> log.error(error.getMessage())).get();
	}
	
	@Autowired
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(elasticsearchClient());
	}
	
	/**
	 * 客户端配置
	 *
	 * @return {@link ClientConfiguration}
	 */
	private ClientConfiguration clientConfiguration() {
		String[] hostAndPorts =
				properties.getUris().stream().map(w -> w.split("//")[1]).toArray(String[]::new);
		String username = properties.getUsername();
		String password = properties.getPassword();
		Duration connectionTimeout = properties.getConnectionTimeout();
		
		// 不存在username
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
			log.info("hostAndPorts:[{}];", Arrays.toString(hostAndPorts));
			
			return ClientConfiguration.builder()
			                          .connectedTo(hostAndPorts)
			                          .withConnectTimeout(connectionTimeout)
			                          .build();
			
		} else {
			log.info(
					"hostAndPorts:[{}];username:[{}]; password:[{}];",
					Arrays.toString(hostAndPorts)
					, username
					, password);
			
			// 存在username
			return ClientConfiguration.builder()
			                          .connectedTo(hostAndPorts)
			                          .withBasicAuth(username, password)
			                          .withConnectTimeout(connectionTimeout)
			                          .build();
		}
	}
}
