package com.example.hive2es.hive.service;

import com.example.hive2es.entity.ALlEntity;
import com.example.hive2es.entity.vo.entityType.entity.TIntelligencePersonEntity;
import com.example.hive2es.entity.vo.TestEntity;

import java.util.List;
import java.util.Map;

public interface HiveService {

    /**
     * getalljavabean
     *
     * @param tableName 表名
     * @return {@link List<ALlEntity>}
     */
    List<ALlEntity> getALLJavaBean(String tableName);

    /**
     * 这是一个例子，通过以下实现
     *
     * @param map 地图
     * @return {@link List<TestEntity>}
     */
    List<TestEntity> map2JavaBean(Map<String, Object> map);

    List<TIntelligencePersonEntity> PERSON_LIST(List<ALlEntity> aLlEntities);
}
