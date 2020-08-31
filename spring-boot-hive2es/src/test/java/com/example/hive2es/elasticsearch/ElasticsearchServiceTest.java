package com.example.hive2es.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.hive2es.elasticsearch.service.TIntelligencePersonEntityService;
import com.example.hive2es.elasticsearch.service.impl.TIntelligencePersonEntityServiceImpl;
import com.example.hive2es.entity.vo.entityType.entity.TIntelligencePersonEntity;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class ElasticsearchServiceTest {
    TIntelligencePersonEntityService service = null;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void setUp() throws Exception {
        service = new TIntelligencePersonEntityServiceImpl();
    }

    @Test
    public void find() {
        service.findALl();
    }


    @Test
    public void putData() {
        String index = "test4";
        List<TIntelligencePersonEntity> entities = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            TIntelligencePersonEntity entity1 = new TIntelligencePersonEntity();
            entity1.setReId(UUID.randomUUID().toString().replace("-", ""));
            entity1.setName("张三" + i);
            //entity1.setCreatedAt(new Date());
            entities.add(entity1);
        }
        service.bulkInsert(entities);
    }


    @Test
    public void getMapping() {
        try {
            System.out.println(
                    objectMapper.writeValueAsString(
                            service.getMapping("test4").size()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void setCreate() throws IOException {
        String index = "test10";


        String json = getJson();
        System.out.println(json);
        assertTrue(service.createIndex(json));

    }

    @Test
    public void setSetting() {

        List<String> names = Arrays.asList("index.number_of_shards");
        ImmutableOpenMap<String, Settings> setting = service.getSetting(names, "test6");
        System.out.println(setting);
    }

    public String getJson() throws IOException {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("test2"
                + ".json")).getPath();
        return FileUtils.readFileToString(new File(path), "UTF-8");
    }

}
