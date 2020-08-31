package com.example.hive2es.hive.service.impl;

import com.example.hive2es.entity.ALlEntity;
import com.example.hive2es.entity.dto.Source;
import com.example.hive2es.entity.vo.entityType.entity.TIntelligencePersonEntity;
import com.example.hive2es.entity.vo.TestEntity;
import com.example.hive2es.hive.config.TxQueryRunner;
import com.example.hive2es.hive.mapper.SourceJavaBeanMapper;
import com.example.hive2es.hive.service.HiveService;
import com.example.hive2es.hive.utils.HiveHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HiveServiceImpl implements HiveService {
    private static TxQueryRunner tx = null;

    static {
        tx = new TxQueryRunner();
    }

    @Override
    public List<ALlEntity> getALLJavaBean(String tableName) {
        List<Source> sources = HiveHandler.getInstance(tableName).getAllData();

        List<ALlEntity> beans =
                sources.stream()
                        .map(w -> SourceJavaBeanMapper.MAPPER.toJavaBean(w))
                        .collect(Collectors.toList());
        beans.forEach(w -> w.setReId(UUID.randomUUID().toString().replace("-", "")));
        return beans;
    }

    @Override
    public List<TestEntity> map2JavaBean(Map<String, Object> map) {
        ALlEntity aLlEntity = SourceJavaBeanMapper.MAPPER.toJavaBean(new Source(map));
        aLlEntity.setReId(UUID.randomUUID().toString().replace("-", ""));
        return SourceJavaBeanMapper.MAPPER.entitiesToTestVOs(Arrays.asList(aLlEntity));
    }

    @Override
    public List<TIntelligencePersonEntity> PERSON_LIST(List<ALlEntity> aLlEntities) {
        return SourceJavaBeanMapper.MAPPER.entitiesToVOToTPersonEntity(aLlEntities);
    }
}
