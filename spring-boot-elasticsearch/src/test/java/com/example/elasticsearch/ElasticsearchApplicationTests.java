package com.example.elasticsearch;

import com.example.elasticsearch.entity.ExampleEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;

@Slf4j
@SpringBootTest
class ElasticsearchApplicationTests {

    /**
     * @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
     * 由于AbstractElasticsearchConfiguration中已经注入了elasticsearchOperations和
     * elasticsearchTemplate，本质上这两个函数中的方法并没有什么不同，所以直接使用elasticsearchTemplate
     * 就可以了
     */
    @Autowired
    private ElasticsearchRestTemplate template;
    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchOperations operations;
    //TODO 注意：如果查看源码就能看到elasticsearchRestHighLevelClient和elasticsearchClient其实返回类型都是RestHighLevelClient
    // ，这里自己实现了RestClientConfig，所以这里实现elasticsearchClient就可以了

    @Qualifier("elasticsearchClient")
    @Autowired
    private RestHighLevelClient client;
    final ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads() {



    }
    @Test
    void setCreateIndex(){
    }
    @Test
    void setHealth() throws IOException {
        DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest("mydlq-user");
        Assertions.assertTrue(client.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT).isAcknowledged());
        Assertions.assertTrue(createIndex());

    }

    @Test
    void setTemplate() {
        Assertions.assertTrue(template.indexOps(ExampleEntity.class).delete());
        Assertions.assertTrue(template.indexOps(ExampleEntity.class).create());
        Assertions.assertTrue(template.indexOps(ExampleEntity.class).exists());
    }

    @Test
    void setClient() throws IOException {
        final TermsAggregationBuilder aggregation = AggregationBuilders.terms("top_tags")
                .field("tags")
                .order(BucketOrder.count(false));

        final SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(aggregation);
        final SearchRequest searchRequest = new SearchRequest().indices("blog")
                .source(builder);
        client.search(searchRequest, RequestOptions.DEFAULT);


    }


    /**
     * 创建索引
     */
    public boolean createIndex() {
        try {
            // 创建 Mapping
            XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", true)
                    .startObject("properties")
                    .startObject("name")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("address")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("remark")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("age")
                    .field("type", "integer")
                    .endObject()
                    .startObject("salary")
                    .field("type", "float")
                    .endObject()
                    .startObject("birthDate")
                    .field("type", "date")
                    .field("format", "yyyy-MM-dd")
                    .endObject()
                    .startObject("createTime")
                    .field("type", "date")
                    .endObject()
                    .endObject()
                    .endObject();
            // 创建索引配置信息，配置
            Settings settings = Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 0)
                    .build();
            // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
            CreateIndexRequest request = new CreateIndexRequest("mydlq-user");
            request.settings(settings).mapping(mapping);


            // RestHighLevelClient 执行创建索引
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

            // 判断是否创建成功
            return response.isAcknowledged();

        } catch (IOException e) {
            log.error("", e);
        }
        return false;
    }
}
