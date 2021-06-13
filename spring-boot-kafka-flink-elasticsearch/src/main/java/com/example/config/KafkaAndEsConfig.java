package com.example.config;

import io.vavr.control.*;
import lombok.extern.slf4j.*;
import org.apache.flink.streaming.connectors.elasticsearch7.*;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.impl.client.*;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.elasticsearch.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

import java.util.*;
import java.util.stream.*;

/**
 * kafka和es的bean配置
 *
 * @author shizeying
 * @date 2021/06/13
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ElasticsearchRestClientProperties.class)
public class KafkaAndEsConfig {
	@Autowired
	private ElasticsearchRestClientProperties elasticsearchRestClientProperties;
	
	@Autowired
	private KafkaCustomProperties properties;
	
	
	@Bean
	public Properties consumerConfigs() {
		
		Properties props = new Properties();
		
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", properties
				                                                                            .getBootstrapServers()));
		//zk
		props.setProperty("zookeeper.connect", String.join(",", properties
				                                                        .getZookeeperConnects()));
		//spring.kafka.consumer.group-id
		Try.of(() -> properties.getConsumer().getGroupId())
		   .filter(Objects::nonNull)
		   .forEach(groupId -> props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId));
		//spring.kafka.consumer.key-deserializer
		Try.of(() -> properties.getConsumer().getKeyDeserializer())
		   .filter(Objects::nonNull)
		   .map(Class::getCanonicalName)
		   .forEach(key -> props.setProperty(
				   ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, key));
		//spring.kafka.consumer.value-deserializer
		Try.of(() -> properties.getConsumer().getValueDeserializer())
		   .filter(Objects::nonNull)
		   .map(Class::getCanonicalName)
		   .forEach(value -> props.setProperty(
				   ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, value));
		//spring.kafka.consumer.enable-auto-commit=false
		Try.of(() -> properties.getConsumer().getEnableAutoCommit())
		   .filter(Objects::nonNull)
		   .map(String::valueOf)
		   .forEach(auto -> props.setProperty(
				   ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, auto));
		//spring.kafka.consumer.auto-commit-interval=1
		Try.of(() -> properties.getConsumer().getAutoCommitInterval())
		   .filter(Objects::nonNull)
		   .forEach(inter -> props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, inter.toString()));
		
		// 位移丢失和位移越界后的恢复起始位置
		//spring.kafka.consumer.auto-offset-reset=earliest
		Try.of(() -> properties.getConsumer().getAutoOffsetReset())
		   .filter(Objects::nonNull)
		   .forEach(reset -> props.setProperty(
				   ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, reset));
		
		
		return props;
	}
	
	@Bean
	public List<HttpHost> getHttpHosts() {
		return elasticsearchRestClientProperties.getUris()
		                                        .stream()
		                                        .filter(Objects::nonNull)
		                                        .map(HttpHost::create)
		                                        .collect(Collectors.toList());
	}
	
	@Bean
	public RestClientFactory getRestClientFactory() {
		return restClientBuilder -> restClientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder ->
				                                                                          Try.of(BasicCredentialsProvider::new)
				                                                                             .peek(credentialsProvider -> credentialsProvider
						                                                                                                          .setCredentials(
								                                                                                                          AuthScope.ANY,
								                                                                                                          new UsernamePasswordCredentials(
										                                                                                                          elasticsearchRestClientProperties
												                                                                                                          .getUsername(),
										                                                                                                          elasticsearchRestClientProperties
												                                                                                                          .getPassword())))
				                                                                             .map(httpAsyncClientBuilder::setDefaultCredentialsProvider)
				                                                                             .get());
	}
	
}
