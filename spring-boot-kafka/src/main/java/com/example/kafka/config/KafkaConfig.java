//package com.example.kafka.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListenerConfigurer;
//import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//@Component
//@Configuration
//@EnableKafka
//public class KafkaConfig implements KafkaListenerConfigurer {
//  @Autowired
//  private LocalValidatorFactoryBean validator;
//
//  @Override
//  public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
//    registrar.setValidator(this.validator);
//  }
//}
