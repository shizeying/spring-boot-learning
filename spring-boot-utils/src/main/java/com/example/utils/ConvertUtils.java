package com.example.utils;

import io.vavr.*;

import java.time.*;
import java.time.format.*;
import java.util.function.*;

public class ConvertUtils {
	
	/**
	 * {@code content} 从kafka中接收的内容
	 * {@code fileNameIndex} 当前文件的前缀
	 * {@code head}   csv的请求头
	 */
	public static Function3<String, String, String, Tuple3<String, String, String>> formatData = (content, fileNameIndex, head) -> {
		String fileName = fileNameIndex + "_" + DateTimeFormatter
				                                        .ofPattern("yyyy_MM_dd")
				                                        .format(LocalDate.now())
				                  + ".csv";
		return Tuple.of(fileName, content, head);
	};
	/**
	 * 输入:{@link Tuple3<String, String, String> tuple3}
	 * <p>
	 * <blockquote> <pre>
	 * {@code content} 从kafka中接收的内容
	 * {@code fileNameIndex} 当前文件的前缀
	 * {@code head}   csv的请求头
	 * </pre></blockquote>
	 * <p>
	 * 输出：{@link Tuple3<String, String, String> formatDataTuple3}
	 * <p>
	 * <blockquote> <pre>
	 *        {@code tuple._1} fileName  写入的文件名
	 *        {@code tuple._2} content   写入的内容
	 *        {@code tuple._3} head      写入的文件头部
	 * </pre></blockquote>
	 * </p>
	 */
	public static Function<Tuple3<String, String, String>, Tuple3<String, String, String>> formatDataTuple3 = tuple3 -> {
		String fileName = tuple3._2 + "_" + DateTimeFormatter
				                                    .ofPattern("yyyy_MM_dd")
				                                    .format(LocalDate.now())
				                  + ".csv";
		//(content, fileNameIndex, head)
		return Tuple.of(fileName, tuple3._1, tuple3._2);
	};
	public static final Consumer<Boolean> println = str -> System.out.println("输出的信息：【" + str + "】");
	public static final Consumer<String> printlnConsumer = str -> System.out.println("拉去的kafka消息如下：【" + str + "】");
	
	
}
