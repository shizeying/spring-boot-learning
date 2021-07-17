package com.example.utils;

import cn.hutool.core.io.resource.*;
import cn.hutool.core.text.csv.*;
import cn.hutool.poi.excel.*;
import com.example.utils.config.*;
import com.fasterxml.jackson.databind.*;
import io.vavr.control.*;
import lombok.extern.slf4j.*;
import org.apache.commons.io.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

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
	private static final Function<String, String> readFileToString = queryFile -> Try.of(() -> new ClassPathResource(QUERY_LOCATION + queryFile))
	                                                                                 .mapTry(ClassPathResource::getInputStream)
	                                                                                 .mapTry(inputStream -> IOUtils
			                                                                                                        .toString(inputStream,
					                                                                                                        StandardCharsets.UTF_8))
	                                                                                 .getOrElseThrow(() -> new NullPointerException(
			                                                                                 "没有匹配到指定文件中的数据"));
	/**
	 * 将json转换成对应的JSONNODE
	 */
	public static Function<String, JsonNode> stringToJsonNode = queryFile -> Try.of(() -> JSON_PATH + queryFile)
	                                                                            .map(readFileToString)
	                                                                            .map(JacksonUtil::readJson)
	                                                                            .onFailure(Throwable::printStackTrace)
	                                                                            .get();
	
	public static Function<String, Supplier<Stream<JsonNode>>> stringToJsonNodeStream = queryFile -> () -> Try.of(() -> JSON_PATH + queryFile)
	                                                                                                          .map(readFileToString)
	                                                                                                          .map(JacksonUtil::readJson)
	                                                                                                          .map(jsonNode ->
			                                                                                                               StreamSupport
					                                                                                                               .stream(jsonNode.spliterator(),
							                                                                                                               false)
	
	
	                                                                                                          )
	                                                                                                          .onFailure(Throwable::printStackTrace)
	                                                                                                          .get();
	public static final Function<String, InputStream> getInputStream = queryFile -> Try.of(() -> new ClassPathResource(queryFile))
	                                                                                   .mapTry(ClassPathResource::getInputStream)
	
	                                                                                   .getOrElseThrow(() -> new NullPointerException(
			                                                                                   "没有匹配到指定文件中的数据"));
	
	/**
	 * 获得资源excel映射列表
	 *
	 * @param resourceFileNamePath 资源文件名路径
	 * @param sheet                表
	 * @return {@link Stream<Map<String, Object>>}
	 */
	public static Supplier<Stream<Map<String, Object>>> getResourceExcelMapListSupplier(String resourceFileNamePath, Integer sheet) {
		
		return () -> Try.of(() -> new ClassPathResource(resourceFileNamePath))
		                .mapTry(ClassPathResource::getInputStream)
		                .mapTry(inputStream -> ExcelUtil.getReader(inputStream, Optional
				                                                                        .ofNullable(sheet).orElse(0)))
		                .mapTry(ExcelReader::readAll)
		                .orElse(getFullPathExcelMapList(resourceFileNamePath, sheet))
		                .getOrElseThrow(() -> new NullPointerException(
				                "没有匹配到指定文件中的数据:{" + resourceFileNamePath + "}"))
		                .stream();
		
	}
	
	private static Try<List<Map<String, Object>>> getFullPathExcelMapList(String fileNamePath, Integer sheet) {
		return Try.of(() -> ExcelUtil.getReader(
				Objects.requireNonNull(fileNamePath, "文件路径为空"), Optional
						                                                .ofNullable(sheet).orElse(0)))
		          .mapTry(ExcelReader::readAll);
	}
	
	/**
	 * 得到完整路径excel映射列表
	 *
	 * @param fileNamePath 文件名的路径
	 * @param sheet        表
	 * @return {@link Stream<Map<String, String>>}
	 */
	public static Supplier<Stream<Map<String, Object>>> getFullPathExcelMapListSupplier(String fileNamePath, Integer sheet) {
		return () -> Try.of(() -> ExcelUtil.getReader(
				Objects.requireNonNull(fileNamePath, "文件路径为空"), Optional
						                                                .ofNullable(sheet).orElse(0)))
		                .mapTry(ExcelReader::readAll)
		                .getOrElseThrow(() -> new NullPointerException(
				                "没有匹配到指定文件中的数据:{" + fileNamePath + "}")).stream();
	}
	
	public static Supplier<Stream<Map>> getResourceCSVMapListSupplier(String resourceFileNamePath) {
		
		return () -> Try.of(() -> new ClassPathResource(resourceFileNamePath))
		                .mapTry(ClassPathResource::getInputStream)
		                .mapTry(InputStreamReader::new)
		                .mapTry(inputStream -> CsvUtil.getReader().read(inputStream, Map.class))
		                .orElse(getFullPathCSVMapList(resourceFileNamePath))
		                .getOrElseThrow(() -> new NullPointerException(
				                "没有匹配到指定文件中的数据:{" + resourceFileNamePath + "}")).stream();
		
	}
	
	public static Supplier<Stream<Map>> getFullPathCSVMapListSupplier(String fileNamePath) {
		
		return () -> CsvUtil.getReader().read(ResourceUtil.getUtf8Reader(Objects.requireNonNull(fileNamePath, "文件路径为空")), Map.class).stream();
	}
	
	private static Try<List<Map>> getFullPathCSVMapList(String fileNamePath) {
		
		return Try.of(() -> CsvUtil.getReader().read(ResourceUtil.getUtf8Reader(Objects.requireNonNull(fileNamePath, "文件路径为空")), Map.class));
	}
	
}
