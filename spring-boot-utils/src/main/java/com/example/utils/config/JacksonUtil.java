package com.example.utils.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@SuppressWarnings("ALL")
public class JacksonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String bean2Json(Object obj) {
		return Try.of(() -> mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> T json2BeanByType(byte[] data, Class<T> clazz) {
		
		return Try.of(() -> mapper.readValue(data, clazz))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> T json2BeanByTypeReference(String jsonStr, TypeReference<T> toValueTypeRef) {
	mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		return Try.of(() -> mapper.readValue(jsonStr, toValueTypeRef))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	public static <T> T json2LongByTypeReference(String jsonStr, TypeReference<T> toValueTypeRef) {
		return Try.of(() -> mapper.readValue(jsonStr, toValueTypeRef))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	public static <T> Map convertValue(T entity) {
		return mapper.convertValue(entity, Map.class);
	}
}

