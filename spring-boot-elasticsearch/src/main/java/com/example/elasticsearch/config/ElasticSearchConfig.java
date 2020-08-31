package com.example.elasticsearch.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
/**
 * ElasticSearch配置文件
 * Es7使用RestHighLevelClient操作ES
 */
@SpringBootConfiguration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticSearchConfig {
    private String host;
    private Integer port;

    /**
     * 创建RestHighLevelClient 实例
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost http = new HttpHost(host, port, "http");
        return new RestHighLevelClient(RestClient.builder(http));
    }
}
