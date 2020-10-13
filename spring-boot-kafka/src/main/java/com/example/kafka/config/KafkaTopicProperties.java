package com.example.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@Configuration
@ConfigurationProperties(prefix = "kafka.topic")
public class KafkaTopicProperties {
  private String elasticsearchToMongo;
  private String mongoToElasticsearch;

  public String getElasticsearchToMongo() {
    return elasticsearchToMongo;
  }

  public void setElasticsearchToMongo(String elasticsearchToMongo) {
    this.elasticsearchToMongo = elasticsearchToMongo;
  }

  public String getMongoToElasticsearch() {
    return mongoToElasticsearch;
  }

  public void setMongoToElasticsearch(String mongoToElasticsearch) {
    this.mongoToElasticsearch = mongoToElasticsearch;
  }
}
