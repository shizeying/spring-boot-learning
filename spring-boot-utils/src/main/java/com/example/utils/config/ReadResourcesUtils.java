package com.example.utils.config;

import com.fasterxml.jackson.databind.*;
import io.vavr.control.*;
import lombok.extern.slf4j.*;
import org.apache.commons.io.*;
import org.springframework.core.io.*;

import java.io.*;
import java.nio.charset.*;
import java.util.function.*;

/**
 * 读取resources中的文件
 *
 * @author shizeying
 * @date 2021/06/12
 */
@Slf4j
public class ReadResourcesUtils {
	private final static String QUERY_LOCATION = "python/";
	private final static String JSON_PATH = "json/";
	
	
	/**
	 * 读取Resource先的json
	 */
	private static Function<String, String> readFileToString = queryFile -> Try.of(() -> new ClassPathResource(QUERY_LOCATION + queryFile))
	                                                                           .mapTry(ClassPathResource::getInputStream)
	                                                                           .mapTry(inputStream -> IOUtils
			                                                                                                  .toString(inputStream,
					                                                                                                  StandardCharsets.UTF_8))
	                                                                           .getOrElseThrow(() -> new NullPointerException("没有匹配到指定文件中的数据"));
	/**
	 * 将json转换成对应的JSONNODE
	 */
	public static Function<String, JsonNode> stringToJsonNode = queryFile -> Try.of(() -> JSON_PATH + queryFile)
	                                                                            .map(readFileToString)
	                                                                            .map(JacksonUtil::readJson)
	                                                                            .onFailure(Throwable::printStackTrace)
	                                                                            .get();
	
	
	public static Function<String, InputStream> readFileToStringFile = queryFile -> Try.of(() -> new ClassPathResource(queryFile))
	                                                                                   .mapTry(ClassPathResource::getInputStream)
	                                                                                   //.mapTry(inputStream -> IOUtils
	                                                                                   //                          .toString(inputStream,
	                                                                                   //                                  StandardCharsets.UTF_8))
	                                                                                   .getOrElseThrow(
			                                                                                   () -> new NullPointerException("没有匹配到指定文件中的数据"));
	public static Function<String, String> readFileToStringFilePath = queryFile -> Try.of(() -> new ClassPathResource(queryFile))
	                                                                                  .map(ClassPathResource::getPath)
	                                                                                   //.mapTry(ClassPathResource::getInputStream)
	                                                                                   //.mapTry(inputStream -> IOUtils
	                                                                                   //                          .toString(inputStream,
	                                                                                   //                                  StandardCharsets.UTF_8))
	                                                                                   .getOrElseThrow(
			                                                                                   () -> new NullPointerException("没有匹配到指定文件中的数据"));
	
}
