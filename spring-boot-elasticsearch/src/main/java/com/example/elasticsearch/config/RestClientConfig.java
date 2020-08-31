package com.example.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ElasticSearch配置文件
 * Es7使用RestHighLevelClient操作ES
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {
    @Autowired
    private ElasticsearchRestClientProperties properties;

    /**
     * 无权限
     *
     * @return {@link RestHighLevelClient}
     */
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(properties.getUris().stream().map(w->w.split("//")[1]).toArray(String[]::new))
                .build();

        return RestClients.create(clientConfiguration).rest();
    }


    ///**
    // * 有权限
    // *
    // * @return {@link RestHighLevelClient}
    // */
    //@Override
    //@Bean
    //public RestHighLevelClient elasticsearchClient() {
    //    HttpHost[] httpHostArr = Stream.of(properties.getUris().stream().map(w -> w.split("//")[1]).toArray(String[]::new)).map(HttpHost::create).toArray(HttpHost[]::new);
    //    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    //    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(properties.getUsername(),
    //            properties.getPassword()));
    //
    //
    //
    //    RestHighLevelClient client = new RestHighLevelClient(
    //            RestClient.builder(httpHostArr)
    //                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
    //                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
    //                            httpClientBuilder.disableAuthCaching();
    //                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    //                        }
    //                    }));
    //    return client;
    //}



}