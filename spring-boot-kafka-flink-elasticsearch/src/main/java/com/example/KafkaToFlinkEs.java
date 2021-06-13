package com.example;

import com.example.config.*;
import com.example.handler.*;
import com.example.utils.config.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import lombok.extern.slf4j.*;
import lombok.*;
import org.apache.flink.api.common.serialization.*;
import org.apache.flink.streaming.api.environment.*;
import org.apache.flink.streaming.connectors.elasticsearch.*;
import org.apache.flink.streaming.connectors.elasticsearch7.*;
import org.apache.flink.streaming.connectors.kafka.*;
import org.apache.http.*;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.elasticsearch.*;
import org.springframework.boot.autoconfigure.kafka.*;
import org.springframework.context.*;

import java.util.*;

/**
 * kafka导入elasticsearch 带密码验证的demo
 * <p>
 * <blockquote><pre>
 *          {
 *              "elasticsearchIndex":"es索引名称",
 *              "inputJson":{
 *                  "id":"id",
 *                  ..数据...
 *              }
 *
 *          }
 *
 *      </pre></blockquote>
 *
 * </p>
 *
 * @author shizeying
 * @date 2021/06/13
 */
@Slf4j
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class, ElasticsearchRestClientAutoConfiguration.class})
public class KafkaToFlinkEs {
	
	
	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(KafkaToFlinkEs.class, args);
		final RestClientFactory restClientFactory = context.getBean(RestClientFactory.class);
		final List<HttpHost> httpHosts = (List<HttpHost>) Objects.requireNonNull(context.getBean("getHttpHosts"), "getHttpHosts未获取到");
		
		
		final Properties consumerConfig = (Properties) Objects.requireNonNull(context.getBean("consumerConfigs"), "consumerConfigs未获取到");
		
		final String topic = Optional.of(context.getBean(KafkaCustomProperties.class))
		                             .map(KafkaCustomProperties::getTopic)
		                             .orElseThrow(() -> new NoSuchElementException("topic未配置"));
		
		
		final int bulkSize = context.getBean(KafkaCustomProperties.class).getElasticsearchIndexBulk();
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		final FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), consumerConfig);
		//设置只读取最新数据
		consumer.setStartFromEarliest();
		//添加kafka为数据源
		val stream = env.addSource(consumer);
		stream.printToErr();
		ElasticsearchSink.Builder<String> esSinkBuilder
				= new
						
						  ElasticsearchSink.Builder<>
						  (httpHosts,
								  (ElasticsearchSinkFunction<String>)
										  (json, runtimeContext,
										   requestIndexer) -> {
											  Optional<JsonNode> optional = Optional.of(
													  JacksonUtil.readJson(json));
											  String index = optional.get().get("elasticsearchIndex").asText();
											  final Optional<JsonNode> optionalInputJson = Optional.ofNullable(optional.get().get("inputJson"));
											
											  final String id = optionalInputJson.map(node -> node.get("id").asText())
											                                     .orElseThrow(
													                                     () -> new NoSuchElementException(
															                                     "未匹配到id"));
											  ObjectNode objectNode = optionalInputJson.get()
											                                           .deepCopy();
											  objectNode.remove("id");
											  String newJson = JacksonUtil.bean2JsonNotNUll(objectNode);
											  Requests.indexRequest(index).id(id)
											          .source(newJson, XContentType.JSON);
											  log.info("data saved.");
										  });
		esSinkBuilder.setRestClientFactory(restClientFactory);
		//批量请求的配置；这将指示接收器在每个元素之后发出请求，否则将对它们进行缓冲。
		esSinkBuilder.setBulkFlushMaxActions(bulkSize);
		
		esSinkBuilder.setFailureHandler(new RetryRejectedExecutionFailureHandler());
		try {
			env.execute("Kafka_Flink_Elasticsearch");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
