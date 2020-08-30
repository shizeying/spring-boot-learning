package com.example.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;


@EnableMongoRepositories
@SpringBootApplication
public class MongodbApplication {

  public static void main(String[] args) {
    SpringApplication.run(MongodbApplication.class, args);
  }
  
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
