package com.example.utils.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

/**
 * jackson 工具类
 *
 * @author shizeying
 * @date 2021/06/12
 */
@Slf4j
public class JacksonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static Function<Object, String> bean2JsonFun = obj -> Try.of(() -> mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
					.writerWithDefaultPrettyPrinter().writeValueAsString(obj))
			.onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	
	public static String bean2Json(Object obj) {
		return Try.of(() -> mapper.writeValueAsString(obj))
				.onFailure(error -> log.error("JacksonUtil:[{}]", error.getMessage())).get();
	}
	
	public static String bean2JsonNotNUll(Object obj) {
		return Try.of(() -> mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(obj))
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
	
	public static JsonNode readJson(String content) {
		return Try.of(() -> mapper.readTree(content))
				.onFailure(err -> new MyErrorException(err, JacksonUtil.class))
				.get();
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
