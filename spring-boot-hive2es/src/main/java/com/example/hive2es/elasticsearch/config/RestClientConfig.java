package com.example.hive2es.elasticsearch.config;

import com.example.hive2es.utils.GetYaml;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

;

/**
 * rest客户端配置 ElasticSearch配置文件 Es7使用RestHighLevelClient操作ES
 *
 * @author shizeying
 * @date 2020/09/01
 */
@Slf4j
public class RestClientConfig {

    private static RestClientConfig restClientConfig;

    private RestClientConfig() {
    }

    private static Map<String, String> yamlMap = new HashMap<>();

    public static RestClientConfig getInstance() {
        if (restClientConfig == null) {
            synchronized (RestClientConfig.class) {
                if (restClientConfig == null) {
                    restClientConfig = new RestClientConfig();
                    //TODO 通过elasticsearch.yaml获取
                    yamlMap = GetYaml.getInstance().getYamlMap("elasticsearch.yml");
                }
            }
        }
        return restClientConfig;
    }

    public RestHighLevelClient elasticsearchClient() {
        // TODO uris
        String uris = Optional.ofNullable(yamlMap.get("uris")).orElse("http://localhost:9200");
        // TODO username
        String username = Optional.ofNullable(yamlMap.get("username")).orElse("");
        // TODO password
        String password = Optional.ofNullable(yamlMap.get("password")).orElse("");
        HttpHost[] httpHosts =
                Stream.of(uris)
                        .map(
                                uri -> {
                                    String http = uri.split("//")[0].replace(":", "");
                                    String host = uri.split("//")[1].split(":")[0];
                                    int port = Integer.parseInt(uri.split("//")[1].split(":")[1]);
                                    return new HttpHost(host, port, http);
                                })
                        .toArray(HttpHost[]::new);

        if (username == null || " ".equals(username)) {
            return new RestHighLevelClient(RestClient.builder(httpHosts));
        }
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder builder =
                RestClient.builder(httpHosts)
                        .setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(builder);
    }

    public void closeRestClientConfig(RestHighLevelClient restHighLevelClient) {
        if (restHighLevelClient != null) {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
