package com.example.utils.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("ALL")
public class JacksonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String bean2Json(Object obj) {
		return Try.of(() -> mapper.writeValueAsString(obj))
				.onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> T json2BeanByType(byte[] data, Class<T> clazz) {
		
		return Try.of(() -> mapper.readValue(data, clazz))
				.onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> T json2Bean(String jsonStr, Class<T> clazz) {
		
		return Try.of(() -> mapper.readValue(jsonStr, clazz))
				.onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> Map convertValue(T entity) {
		return mapper.convertValue(entity, Map.class);
	}
}

