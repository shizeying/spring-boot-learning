package com.example.kafka.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@SuppressWarnings("ALL")
public class KafkaMessageListener {
	
	
	@KafkaListener(
			groupId = "${spring.kafka.consumer.group-id}",
			containerFactory = "kafkaListenerContainerFactory",
			autoStartup = "true",
			errorHandler = "listenErrorHandler",
			topics = "${kafka.topic.elasticsearch-to-mongo}"
	)
	public void listen(ConsumerRecord record,
			Acknowledgment acknowledgment) {
		try {
			Optional<ConsumerRecord<String, String>> kafkaMessage = Optional.ofNullable(record);
			if (kafkaMessage.isPresent()) {
				String value = kafkaMessage.get().value();
				String key = kafkaMessage.get().key();
				String clazzName = key.split("#")[1];
				
				//log.info("----------------- record ={}", record);
				//log.info("------------------ value ={}", value);
				//log.info("------------------ key ={}", key);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// 手动提交 offset
			acknowledgment.acknowledge();
			log.info("消费成功");
		}
		
		
	}
	
}
