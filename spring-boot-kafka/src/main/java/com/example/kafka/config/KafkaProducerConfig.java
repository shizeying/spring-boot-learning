package com.example.kafka.config;

import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {
  @Autowired private KafkaProperties properties;

  @Bean
  public ProducerFactory<String, String> producerFactory() {

    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    //指定多个kafka集群多个地址
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    // 重试次数，0为不启用重试机制
    //spring.kafka.producer.retries=0
    props.put(ProducerConfig.RETRIES_CONFIG, properties.getProducer().getRetries());
    // acks=0 把消息发送到kafka就认为发送成功
    // acks=1 把消息发送到kafka leader分区，并且写入磁盘就认为发送成功
    // acks=all 把消息发送到kafka leader分区，并且leader分区的副本follower对消息进行了同步就任务发送成功
    //spring.kafka.producer.acks=-1
    props.put(ProducerConfig.ACKS_CONFIG,properties.getProducer().getAcks());
    // 生产者空间不足时，send()被阻塞的时间，默认60s
    //props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60);
    // 键的序列化方式
    //spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getProducer().getKeySerializer());
    // 值的序列化方式
    //spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getProducer().getValueSerializer());
    //spring.kafka.producer.client-id=1
    props.put(ProducerConfig.CLIENT_ID_CONFIG, properties.getProducer().getClientId());
    //spring.kafka.producer.compression-type=none
    props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,properties.getProducer().getCompressionType());
    //spring.kafka.producer.batch-size=1
    //props.put(ProducerConfig.BATCH_SIZE_CONFIG,properties.getProducer().getBatchSize() );
    //spring.kafka.producer.buffer-memory=33554432
    //props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,properties.getProducer().getBufferMemory());
    
    return props;
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate(producerFactory());
  }
}
