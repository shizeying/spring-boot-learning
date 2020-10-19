package com.example.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * @doc spring data mongodb 方式插入数据存在_class，去除方法
 * @desc TODO
 * @version 1.0.0
 * @author shizeying
 * @date 2020/10/19
 */@Configuration
public class MongoConfigListener implements ApplicationListener<ContextRefreshedEvent> {
  @Autowired private MongoTemplate mongoTemplate;
  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    MongoConverter converter = mongoTemplate.getConverter();
    if (converter.getTypeMapper().isTypeKey("_class")) {
      ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
    }
  }
  
  
  
}
