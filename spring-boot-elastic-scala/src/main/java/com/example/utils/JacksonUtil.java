package com.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@SuppressWarnings("ALL")
public final class JacksonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String bean2Json(Object obj) {
		return Try.of(() -> mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> T json2BeanByType(byte[] data, Class<T> clazz) {
		
		return Try.of(() -> mapper.readValue(data, clazz))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	/**
	 * 下划线转bean方式
	 *
	 * @param jsonStr
	 * 		json str
	 * @param toValueTypeRef
	 * 		对值类型裁判
	 *
	 * @return {@link T}
	 */
	public static <T> T json2BeanBySnakeCaseTypeReference(String jsonStr, TypeReference<T> toValueTypeRef) {
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		return Try.of(() -> mapper.readValue(jsonStr, toValueTypeRef))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> T json2CustomByTypeReference(String jsonStr, TypeReference<T> toValueTypeRef) {
		return Try.of(() -> mapper.readValue(jsonStr, toValueTypeRef))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static <T> Map<String, Object> convertValue(T entity) {
		return mapper.convertValue(entity, new TypeReference<Map<String, Object>>() {
		});
	}
	
	/**
	 * LinkedHashMap
	 *
	 * @param entity
	 * @param <T>
	 *
	 * @return
	 */
	public static <T> Map<String, Object> convertLinkedHashMap(T entity) {
		return mapper.convertValue(entity, new TypeReference<LinkedHashMap<String, Object>>() {
		});
	}
	
	public static <T> Object convertArrayNode(T entity) {
		return mapper.convertValue(entity, Object.class);
	}
}

