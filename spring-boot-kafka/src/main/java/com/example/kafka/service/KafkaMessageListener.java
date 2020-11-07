package com.example.kafka.service;

import io.vavr.control.Try;
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
			containerFactory = "kafkaListenerContainerFactory",
			autoStartup = "true",
			errorHandler = "listenErrorHandler",
			topics = "${kafka.topic.elasticsearch-to-mongo}"
	)
	public void listen(ConsumerRecord record,
			Acknowledgment acknowledgment) {
		System.out.println(record);
		Try.of(
				() ->
						Optional.ofNullable(record))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.onFailure(error -> log.error(error.getMessage(), error))
				.andFinally(() -> {
					acknowledgment.acknowledge();
					log.info("消费成功");
				})
				.forEach(kafkaMessage -> {
					String value = (String) kafkaMessage.key();
					String key = (String) kafkaMessage.value();
				});
		
	}
	
}
