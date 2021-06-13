package com.example.utils.config;

import com.example.exception.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import io.vavr.control.*;
import lombok.extern.slf4j.*;

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
		String json = " {\n" +
				              "     \"elasticsearchIndex\":\"es索引名称\",\n" +
				              "     \"inputJson\":{\n" +
				              "         \"id\":\"id\",\n" +
				              "          \"id2\":\"id\"\n" +
				              "     }}";
		System.out.println(json);
		Optional<JsonNode> optional = Optional.of(
				JacksonUtil.readJson(json));
		String index = optional.get().get("elasticsearchIndex").asText();
		final Optional<JsonNode> optionalInputJson = Optional.ofNullable(optional.get().get("inputJson"));
		
		final String id = optionalInputJson.map(node -> node.get("id").asText())
		                                   .orElseThrow(
				                                   () -> new NoSuchElementException(
						                                   "未匹配到id"));
		ObjectNode objectNode = optionalInputJson.get()
		                                         .deepCopy();
		objectNode.remove("id");
		String newJson = JacksonUtil.bean2JsonNotNUll(objectNode);
		System.out.println(newJson);
	}
	
	
}

