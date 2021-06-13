package com.example.utils.config;

import com.alibaba.fastjson.*;
import com.example.exception.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.*;
import io.vavr.control.*;
import lombok.extern.slf4j.*;

import java.sql.*;
import java.util.*;
import java.util.function.*;

/**
 * jackson 工具类
 *
 * @author shizeying
 * @date 2021/06/12
 */
@Slf4j
public class JacksonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String bean2Json(Object obj) {
		return Try.of(() -> mapper.writeValueAsString(obj))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static String bean2JsonNotNUll(Object obj) {
		return Try.of(() -> mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(obj))
		          .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static Function<Object, String> bean2JsonFun = obj -> Try.of(() -> mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
	                                                                                .writerWithDefaultPrettyPrinter().writeValueAsString(obj))
	                                                                .onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	
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
	
	public static JsonNode readJson(String content) {
		return Try.of(() -> mapper.readTree(content))
		          .getOrElseThrow(JsonNoSuchElementException::new);
	}
	
	public static void main(String[] args) {
		String json = "{\n" +
				              "\"a\":1,\n" +
				              "\"b\":1\n" +
				              "\n" +
				              "}";
		final JsonNode jsonNode = readJson(json);
		ObjectNode objectNode = ((ObjectNode) jsonNode).deepCopy();
		objectNode.remove("a");
		System.out.println(bean2JsonFun.apply(objectNode));
	}
	
	
}

