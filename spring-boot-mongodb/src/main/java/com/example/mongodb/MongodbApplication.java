package com.example.mongodb;

//import com.example.mongodb.config.MongoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;


//@EnableMongoRepositories
//@SpringBootApplication
public class MongodbApplication {

  public static void main(String[] args) {
    //SpringApplication.run(MongodbApplication.class, args);
    String uri="mongodb://data:a@172.16.19.1:27017,172.16.19.2:27017,172.16.19.3:27017";
  
  }
  

  
  
}
