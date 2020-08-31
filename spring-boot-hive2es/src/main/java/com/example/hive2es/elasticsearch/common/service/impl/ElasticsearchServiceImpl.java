package com.example.hive2es.elasticsearch.common.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.hive2es.elasticsearch.common.ConversionUtils;
import com.example.hive2es.elasticsearch.config.RestClientConfig;
import com.example.hive2es.elasticsearch.common.service.ElasticsearchService;
import com.example.hive2es.entity.common.BasicEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 根据继承的类判断并根据实体类的命名进行转换到对应的索引名
 *
 * @author shizeying
 * @date 2020/09/03
 */
@Slf4j
public abstract class ElasticsearchServiceImpl<T extends BasicEntity> implements ElasticsearchService<T> {
    private static final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String INDEX;

    public ElasticsearchServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type actualTypeArgument = pt.getActualTypeArguments()[0];
        String[] split = actualTypeArgument.getTypeName().split("\\.");
        INDEX = ConversionUtils.getInstance().camel2Underline(split[split.length - 1]);
    }


    static {

        // TODO 加载restHighLevelClient
        restHighLevelClient = RestClientConfig.getInstance().elasticsearchClient();
    }

    /**
     * 关闭rest客户端
     */
    @Override
    public void closeRestClient() {
        RestClientConfig.getInstance().closeRestClientConfig(restHighLevelClient);
    }

    @Override
    public boolean existIndex() throws IOException {
        return restHighLevelClient.indices().exists(new GetIndexRequest(INDEX), RequestOptions.DEFAULT);
    }

    @Override
    public boolean createIndex(String source) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(INDEX);
            // 创建索引配置信息，配置
            Settings settings =
                    Settings.builder()
                            .put("index.number_of_shards", 1)
                            .put("index.number_of_replicas", 0)
                            .build();

            request.source(source, XContentType.JSON).settings(settings);

            // RestHighLevelClient 执行创建索引
            CreateIndexResponse response =
                    restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

            // 判断是否创建成功
            return response.isAcknowledged();

        } catch (IOException e) {
            log.error("", e);
        }
        return false;
    }

    @Override
    public Map<String, MappingMetaData> getMapping(String... indices) {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indices);
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        try {
            GetMappingsResponse mapping =
                    restHighLevelClient.indices().getMapping(request, RequestOptions.DEFAULT);
            return mapping.mappings();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * delete索引:可批量
     *
     * @param indices
     * @return boolean
     */
    @Override
    public boolean deleteIndex(String... indices) {
        IndicesClient indicesClient = restHighLevelClient.indices();
        GetIndexRequest request = new GetIndexRequest(indices);

        try {
            if (indicesClient.exists(request, RequestOptions.DEFAULT)) {
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indices);
                AcknowledgedResponse response =
                        indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
                return response.isAcknowledged();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean bulkInsert(List<T> entities) {
        List<IndexRequest> indexRequests = getIndexRequests(entities, INDEX);

        try {

            BulkRequest request = new BulkRequest();
            // TODO 设置超时，等待批处理被执行的超时时间（使用TimeValue形式）
            request.timeout(TimeValue.timeValueMinutes(2));
            // TODO 刷新策略
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
            // TODO
            // 使用ActiveShardCount方式来提供分片副本数：可以是ActiveShardCount.ALL，ActiveShardCount.ONE或ActiveShardCount
            // .DEFAULT（默认）
            request.waitForActiveShards(ActiveShardCount.ALL);

            indexRequests.stream().forEach(request::add);
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            for (BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
                if (bulkItemResponse.isFailed()) {
                    log.error("bulkItemResponse.getFailureMessage():", bulkItemResponse.getFailureMessage());
                    return false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            RestClientConfig.getInstance().closeRestClientConfig(restHighLevelClient);
        }
        return true;
    }

    @Override
    public void findALl() {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        try {
            SearchHits hits = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).getHits();
            for (SearchHit hit : hits) {
                System.err.println(hits.getTotalHits());
                System.out.println(hit.getSourceAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ImmutableOpenMap<String, Settings> getSetting(List<String> names, String... indices) {
        GetSettingsRequest request = new GetSettingsRequest().indices(indices);
        names.forEach(request::names);

        request.includeDefaults(true);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        try {
            return restHighLevelClient
                    .indices()
                    .getSettings(request, RequestOptions.DEFAULT)
                    .getIndexToSettings();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean bulkInsertAsync(List<T> entities) {

        List<IndexRequest> indexRequests = getIndexRequests(entities, INDEX);

        BulkProcessor.Listener listener =
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        int numberOfActions = request.numberOfActions();
                        log.debug("Executing bulk [{}] with {} requests", executionId, numberOfActions);
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        if (response.hasFailures()) {
                            log.warn("Bulk [{}] executed with failures", executionId);
                        } else {
                            log.debug(
                                    "Bulk [{}] completed in {} milliseconds",
                                    executionId,
                                    response.getTook().getMillis());
                        }
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        log.error("Failed to execute bulk", failure);
                    }
                };
        BulkRequest bulkRequest = new BulkRequest();
        // TODO 设置超时，等待批处理被执行的超时时间（使用TimeValue形式）
        bulkRequest.timeout(TimeValue.timeValueMinutes(2));
        // TODO 刷新策略
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        // TODO
        // 使用ActiveShardCount方式来提供分片副本数：可以是ActiveShardCount.ALL，ActiveShardCount.ONE或ActiveShardCount
        // .DEFAULT（默认）
        bulkRequest.waitForActiveShards(ActiveShardCount.ALL);

        BulkProcessor bulkProcessor =
                BulkProcessor.builder(
                        (request, bulkListener) ->
                                restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                        listener)
                        .build();

        BulkProcessor.Builder builder =
                BulkProcessor.builder(
                        (request, bulkListener) ->
                                restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                        listener);
        // TODO 操作数为500时就刷新请求
        builder.setBulkActions(500);
        // TODO 设置何时刷新新的批量请求,根据当前已添加的操作大小（默认为5Mb，使用-1禁用它）
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB)); // TODO 操作大小为1M时就刷新请求
        // TODO 设置允许执行的并发请求数（默认为1，使用0只允许执行单个请求）
        builder.setConcurrentRequests(0); // 不并发执行
        // TODO 设置刷新间隔时间，如果超过了间隔时间，则随便刷新一个BulkRequest挂起（默认为未设置）
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        // TODO 设置一个最初等待1秒,最多重试3次的常量退避策略。
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));

        indexRequests.forEach(bulkProcessor::add);
        try {
            // TODO 用于等待所有请求都已处理或过了指定的等待时间
            return bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bulkProcessor.close();
        }

        return false;
    }

    private List<IndexRequest> getIndexRequests(List<T> entities, String index) {
        return entities.stream()
                .map(
                        entity -> {
                            //TODO 判断是否为空
                            assertTrue(entity != null);
                            String id = entity.getReId();
                            Map map = objectMapper.convertValue(entity, Map.class);
                            IndexRequest indexRequest =
                                    new IndexRequest(index).id(id).source(map, XContentType.JSON);
                            assertNotNull(indexRequest);
                            return indexRequest;
                        })
                .collect(Collectors.toList());
    }
}
