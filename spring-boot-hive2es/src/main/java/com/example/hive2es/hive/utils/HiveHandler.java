package com.example.hive2es.hive.utils;

import com.example.hive2es.hive.config.JdbcUtils;
import com.example.hive2es.elasticsearch.config.RestClientConfig;
import com.example.hive2es.entity.dto.Source;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 根据表明获取全部数据
 *
 * @author shizeying
 * @date 2020/09/02
 */
public class HiveHandler {

    private String tableName;

    private static HiveHandler hiveHandler;


    private static Map<String, String> yamlMap = new HashMap<>();

    public static HiveHandler getInstance(String tableName) {
        if (hiveHandler == null) {
            synchronized (RestClientConfig.class) {
                if (hiveHandler == null) {
                    hiveHandler = new HiveHandler(tableName);

                }
            }
        }
        return hiveHandler;
    }

    private HiveHandler(String tableName) {
        this.tableName = tableName;
    }

    public List<Source> getAllData() {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select * from " + tableName;
        List<Map<String, Object>> mapList = null;
        try {
            mapList = qr.query(sql, new MapListHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Map<String, Object>> maps = new ArrayList<>();

        mapList.stream().forEach(map -> {
            Map<String, Object> objectMap = new HashMap<>();
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {

                String key = stringObjectEntry.getKey();

                Object value = stringObjectEntry.getValue();
                objectMap.put(key.split("\\.")[1], value);

                maps.add(objectMap);
            }
        });
        return maps.stream().map(Source::new).collect(Collectors.toList());
    }


}
