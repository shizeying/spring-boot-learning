package com.example.hive2es.utils;

import com.example.hive2es.elasticsearch.config.RestClientConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

public class GetYaml {

    private static GetYaml getYaml;

    private GetYaml() {
    }


    public static GetYaml getInstance() {
        if (getYaml == null) {
            synchronized (RestClientConfig.class) {
                if (getYaml == null) {
                    getYaml = new GetYaml();
                }
            }
        }
        return getYaml;
    }

    public Map<String, String> getYamlMap(String yamlName) {
        try {
            Yaml yaml = new Yaml();
            URL url = this.getClass().getClassLoader().getResource(yamlName);
            if (url != null) {
                // 也可以将值转换为Map
                Map map = (Map) yaml.load(new FileInputStream(url.getFile()));

                return (Map<String, String>) (map.get(yamlName.replaceAll(".yml", "")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
