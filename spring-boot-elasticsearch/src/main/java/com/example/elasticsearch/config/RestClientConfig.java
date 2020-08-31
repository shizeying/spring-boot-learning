package com.example.elasticsearch.config;

import java.time.Duration;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/** ElasticSearch配置文件 Es7使用RestHighLevelClient操作ES */
@Slf4j
@Component
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {
  @Autowired private ElasticsearchRestClientProperties properties;

  /** @return {@link RestHighLevelClient} */
  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient() {

    return RestClients.create(clientConfiguration()).rest();
  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() {
    return new ElasticsearchRestTemplate(elasticsearchClient());
  }

  /**
   * 客户端配置
   *
   * @return {@link ClientConfiguration}
   */
  private ClientConfiguration clientConfiguration() {
    String[] hostAndPorts =
        properties.getUris().stream().map(w -> w.split("//")[1]).toArray(String[]::new);
    String username = properties.getUsername();
    String password = properties.getPassword();
    Duration connectionTimeout = properties.getConnectionTimeout();

    // TODO 不存在username
    if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
      log.info("hostAndPorts:" + Arrays.toString(hostAndPorts) + ";");

      return ClientConfiguration.builder()
          .connectedTo(hostAndPorts)
          .withConnectTimeout(connectionTimeout)
          .build();

    } else {
      log.info(
          "hostAndPorts:"
              + Arrays.toString(hostAndPorts)
              + ";username:"
              + username
              + "; password:"
              + password
              + ";");

      // TODO 存在username
      return ClientConfiguration.builder()
          .connectedTo(hostAndPorts)
          .withBasicAuth(username, password)
          .withConnectTimeout(connectionTimeout)
          .build();
    }
  }
}
