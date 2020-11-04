package com.example.kafka.service.impl;

import com.example.kafka.service.ConsumerService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

 
  @Override
  @KafkaListener(
      topicPartitions = {
        @TopicPartition(
            topic = "${kafka.topic.elasticsearch-to-mongo}",
            partitions = {"0"})
      },
      groupId = "${spring.kafka.consumer.group-id}",
      containerFactory = "kafkaListenerContainerFactory",
      autoStartup = "true",
      errorHandler = "listenErrorHandler"
  
  )
  public void listen(ConsumerRecord<String, String> record,
      Acknowledgment acknowledgment) {
   
    Optional<ConsumerRecord<String, String>> kafkaMessage = Optional.ofNullable(record);
    if (kafkaMessage.isPresent()) {
      String value = kafkaMessage.get().value();
      String key = kafkaMessage.get().key();
      String clazzName = key.split("#")[1];

      log.info("----------------- record ={}", record);
      log.info("------------------ value ={}", value);
      log.info("------------------ key ={}", key);
    }
    acknowledgment.acknowledge();
  
  }

}
