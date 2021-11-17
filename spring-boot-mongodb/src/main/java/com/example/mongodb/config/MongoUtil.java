//package com.example.mongodb.config;
//
//import com.mongodb.ReadPreference;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.MongoClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.mongo.MongoProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
///** mongo数据读写通用方法 mongodB使用原生方法 */
//@Component
//@Configuration
//public class MongoUtil {
//
//  @Autowired private MongoProperties mongoProperties;
//
//  /** 设置连接参数 */
//  private MongoClientOptions getMongoClientOptions() {
//    MongoClientOptions.Builder builder = MongoClientOptions.builder();
//    // todo 添加其他参数配置
//    // 最大连接数
//    builder.connectionsPerHost(mongoProperties.getPort());
//    MongoClientOptions options = builder.readPreference(ReadPreference.nearest()).build();
//    return options;
//  }
//  /** @return */
//  @Bean
//  public MongoClient getMongoClient() {
//    System.err.printf(
//        "mongodb.host:%s;mongodb.port:%s\n", mongoProperties.getHost(), mongoProperties.getPort());
//
//    return new MongoClient(
//        new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()),
//        getMongoClientOptions());
//  }
//
//  //
//
//}
