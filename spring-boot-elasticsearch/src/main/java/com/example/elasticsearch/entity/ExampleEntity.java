package com.example.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * egg：禁止自动创建index，设置为手动创建，避免自动创建报错
 *
 * @author shizeying
 * @date 2020/08/31
 */
@Data
@Document(indexName = "todo", createIndex = false, shards = 1, replicas = 1, refreshInterval = "1s")
public class ExampleEntity {

    @Id
    private String id;

    private String title;

    private String desc;
    @GeoPointField
    private GeoPoint geoPoint;
}
