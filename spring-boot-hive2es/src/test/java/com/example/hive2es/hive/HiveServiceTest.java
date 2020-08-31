package com.example.hive2es.hive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.hive2es.hive.service.HiveService;
import com.example.hive2es.hive.service.impl.HiveServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HiveServiceTest {
    HiveService service = null;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp()  {
        service = new HiveServiceImpl();
    }
    @Test
    public void  setService(){
        String tableName="t_intelligence_person";
        //List<T_Intelligence_Person> lists = service.getALLJavaBean(tableName);
        //try {
        //    System.out.println(objectMapper.writeValueAsString(lists.get(0)));
        //} catch (JsonProcessingException e) {
        //    e.printStackTrace();
        //}

    }
    @Test
    public void test() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "zhangsan1");
        map.put("name", "zhangsan");
        map.put("lot", "142342");
        map.put("lat", "45678");
        map.put("abs", "abs");
        map.put("origin", "origin");

    System.out.println(objectMapper.writeValueAsString(service.map2JavaBean(map).get(0)));
    }


}
