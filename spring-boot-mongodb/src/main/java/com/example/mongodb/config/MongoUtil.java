package com.example.mongodb.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;

/** mongo数据读写通用方法 */
public class MongoUtil {
  @Value("spring.data.mongodb.port")
  private int mongoPort;

  @Value("spring.data.mongodb.host")
  private String mongoHost;

  private static volatile MongoUtil instance = null;

  private MongoUtil() {}

  public static MongoUtil getInstance() {
    try {
      if (instance != null) {

      } else {
        // 创建实例之前可能会有一些准备性的耗时工作
        Thread.sleep(300);
        synchronized (MongoUtil.class) {
          if (instance == null) { // 二次检查
            instance = new MongoUtil();
          }
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return instance;
  }

  private MongoClient mongoClient = null;

  public MongoClient getMongoClient() {
    if (mongoClient == null) {
      MongoClientOptions options =
          MongoClientOptions.builder().connectTimeout(60000).socketTimeout(60000).build();
      System.out.println(mongoHost);
      mongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort), options);
    }

    return mongoClient;
  }

  public void close() {
    if (mongoClient != null) {
      mongoClient.close();
    }
  }
}
