package com.example.kafka.config;

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
    props.put(ProducerConfig.RETRIES_CONFIG, 0);
    // acks=0 把消息发送到kafka就认为发送成功
    // acks=1 把消息发送到kafka leader分区，并且写入磁盘就认为发送成功
    // acks=all 把消息发送到kafka leader分区，并且leader分区的副本follower对消息进行了同步就任务发送成功
    props.put(ProducerConfig.ACKS_CONFIG,properties.getProducer().getAcks());
    // 生产者空间不足时，send()被阻塞的时间，默认60s
    props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 6000);
    // 键的序列化方式
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getProducer().getKeySerializer());
    // 值的序列化方式
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getProducer().getValueSerializer());
    props.put(ProducerConfig.CLIENT_ID_CONFIG, properties.getProducer().getClientId());
    return props;
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate(producerFactory());
  }
}
