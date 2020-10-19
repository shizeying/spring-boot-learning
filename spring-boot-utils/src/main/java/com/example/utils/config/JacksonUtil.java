package com.example.utils.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonUtil {
  private static ObjectMapper mapper = new ObjectMapper();

  public static String bean2Json(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.info("JacksonUtil报错行：17，代码mapper.writeValueAsString(obj)：{}", e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T json2BeanByType(byte[] data, Class<T> clazz) {
    try {
      return mapper.readValue(data, clazz);
    } catch (IOException e) {
      log.info("JacksonUtil报错行：27，mapper.readValue(data, clazz);：{}", e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T json2Bean(String jsonStr, Class<T> clazz) {
    try {
      return mapper.readValue(jsonStr, clazz);
    } catch (JsonProcessingException e) {
      log.info("JacksonUtil报错行：38，mapper.readValue(data, clazz);：{}", e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
}
