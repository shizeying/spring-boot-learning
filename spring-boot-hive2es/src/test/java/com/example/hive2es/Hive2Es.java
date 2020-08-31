package com.example.hive2es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.hive2es.elasticsearch.common.ConversionUtils;
import com.example.hive2es.elasticsearch.service.TIntelligencePersonEntityService;
import com.example.hive2es.elasticsearch.service.impl.TIntelligencePersonEntityServiceImpl;
import com.example.hive2es.entity.ALlEntity;
import com.example.hive2es.entity.vo.entityType.entity.TIntelligencePersonEntity;
import com.example.hive2es.hive.constant.TableConstants;
import com.example.hive2es.hive.service.HiveService;
import com.example.hive2es.hive.service.impl.HiveServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Hive2Es {

    private TIntelligencePersonEntityService elasticsearchService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private HiveService hiveService;

    @Before
    public void setUp() {
        elasticsearchService = new TIntelligencePersonEntityServiceImpl();
        hiveService = new HiveServiceImpl();
    }

    @Test
    public void hive2Es() throws IOException {

        //TODO 1.获取mapping:mapping必须放在resources/mapping中
        String json = ConversionUtils.getInstance().getJson("tele.json");

        //TODO 2.获取全部List<ALlEntity> TableConstants获取表名
        List<ALlEntity> javaBeans = hiveService.getALLJavaBean(TableConstants.PERSON_ENTITY_TABLE);
        //TODO 3.转换对应的TIntelligencePersonEntity
        List<TIntelligencePersonEntity> t_intelligence_peoples = hiveService.PERSON_LIST(javaBeans);
        //TODO 4.判断是否存在索引
        if (!elasticsearchService.existIndex()) {
            //TODO 4.1创建索引
            if (elasticsearchService.createIndex(json)) {

                //TODO 4.2插入数据
                assertTrue(elasticsearchService.bulkInsert(t_intelligence_peoples));
            }
        } else {
            assertTrue(elasticsearchService.bulkInsert(t_intelligence_peoples));
        }


    }


}
