package com.example.kafka.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Listener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@Configuration
public class KafkaConsumerConfig {
  @Autowired private KafkaProperties properties;

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    //设置偏移量
    factory.getContainerProperties().setAckMode(properties.getListener().getAckMode());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getConsumer().getGroupId());
    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        properties.getConsumer().getKeyDeserializer());
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        properties.getConsumer().getValueDeserializer());
 
    
    // 位移丢失和位移越界后的恢复起始位置
    props.put(
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getConsumer().getAutoOffsetReset());

    return props;
  }

  @Bean
  public Listener listener() {
    return new Listener();
  }

  /** 单消息消费异常处理器 */
  @Bean
  public ConsumerAwareListenerErrorHandler listenErrorHandler() {
    return new ConsumerAwareListenerErrorHandler() {

      @Override
      public Object handleError(
          Message<?> message, ListenerExecutionFailedException e, Consumer<?, ?> consumer) {
        apply(message, e, consumer);
        return null;
      }
    };
  }
  /** 批量息消费异常处理器 */
  @Bean
  public ConsumerAwareListenerErrorHandler listenErrorHandlerBatch() {
    return (message, e, consumer) -> {
      apply(message, e, consumer);
      return null;
    };
  }

  private void apply(
      Message<?> message, ListenerExecutionFailedException e, Consumer<?, ?> consumer) {
    System.err.printf("message:%s\n", message.getPayload());
    System.err.printf("exception:%s\n", e.getMessage());
    consumer.seek(
        new TopicPartition(
            message.getHeaders().get(KafkaHeaders.RECEIVED_TOPIC, String.class),
            message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)),
        message.getHeaders().get(KafkaHeaders.OFFSET, Long.class));
  }
}
