package com.example.hive2es.elasticsearch.common.service;

import com.example.hive2es.entity.common.BasicEntity;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticsearchService<T extends BasicEntity>  {


    /**
     * 创建索引
     *
     * @param source 设置setting和mapping的json
     * @return boolean
     */
    boolean createIndex( String source);

    /**
     * 判断是否存在索引
     *
     * @return boolean
     */
    boolean existIndex() throws IOException;

    /**
     * 关闭rest客户端
     */
    void closeRestClient();


    /**
     * 获取mapping
     *
     * @param indices 指数
     * @return {@link Map<String, MappingMetaData>}
     */
    Map<String, MappingMetaData> getMapping(String... indices);

    /**
     * delete索引:可批量
     *
     * @param indices
     * @return boolean
     */
    boolean deleteIndex(String... indices);


    /**
     * 批量插入
     *
     * @param entities 实体
     * @return boolean
     */
    boolean bulkInsert(List<T> entities);

    /**
     * 异步批量插入
     *
     * @param entities 实体
     * @return boolean
     */
    boolean bulkInsertAsync(List<T> entities);


    /**
     * get设置 index:自己的索引
     * request.names("index.number_of_shards");
     * request.names("index.number_of_replicas");
     *
     * @param names   的名字
     * @param indices 指数
     * @return
     */
    ImmutableOpenMap<String, Settings> getSetting(List<String> names, String... indices);


    /**
     * 这是一个测试类，主要做为测试用的，后续改
     *
     */
    void findALl();
}
