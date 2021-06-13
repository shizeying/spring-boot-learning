package com.example.utils.config;

import com.example.inter.*;
import com.example.utils.*;
import io.vavr.*;
import io.vavr.control.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

/**
 * 文件操作工具类
 *
 * @author shizeying
 * @date 2021/06/12
 */
public class FileOperationsUtils {
	/**
	 * 将数据追加到文件中
	 * {@code filePath}：文件全路径
	 * {@code content}：追加的内容
	 * {@code head}：追加文件的请求头，假入该文件是不存在会默认将其写入
	 */
	public static ThreeConsumer<String, String, String> writeAppendFile =
			(filePath, content, head) -> Try.of(() -> new File(filePath))
			                                .map(FileOperationsUtils.getTuple2)
			                                .map(a -> Tuple.of(a, content, head))
			                                .forEach(FileOperationsUtils.writeData);
	/**
	 * 将数据追加到文件中
	 * {@code tuple3._1 filePath}：文件全路径
	 * {@code tuple3._2 content}：追加的内容
	 * {@code tuple3._3 head}：追加文件的请求头，假入该文件是不存在会默认将其写入
	 */
	public static Consumer<Tuple3<String, String, String>> writeAppendFileTuples =
			tuple3 -> Try.of(() -> new File(tuple3._1))
			             .map(FileOperationsUtils.getTuple2)
			             .map(a -> Tuple.of(a, tuple3._2, tuple3._3))
			             .forEach(FileOperationsUtils.writeData);
	
	/**
	 * 写入数据
	 * 输入：{@link Tuple3<Tuple2<Boolean, File>,String, String> writeData}
	 * <p>
	 * <blockquote><pre>
	 * {@link Tuple2<Boolean, File> } tuple._1:文件是否存在   tuple._2:文件
	 * {@code tuple3._2} content 写入的内容
	 * {@code tuple3._3} head 新文件的头部
	 *     </pre></blockquote>
	 * </p>
	 */
	private static Consumer<Tuple3<Tuple2<Boolean, File>, String, String>> writeData =
			tuple3 -> Try
					          .of(() -> new FileWriter(tuple3._1._2, true))
					
					          .peek(write -> {
						          if (Boolean.FALSE.equals(tuple3._1._1)) {
							          Try.run(() -> {
								          write.write(Objects.requireNonNull(tuple3._3, "head内容为空"));
								          write.write(System.getProperty("line.separator"));
								          write.flush();
							          })
							
							             .onFailure(Throwable::printStackTrace)
							
							          ;
						          }
					          })
					          .forEach(write -> Try.run(() -> {
						          write
								          .write(Objects.requireNonNull(tuple3._2, "写入内容为空"));
						          write.write(System.getProperty("line.separator"));
						          write.flush();
					          })
					                               .andFinallyTry(write::close)
					                               .onFailure(Throwable::printStackTrace));
	
	
	/**
	 * 获取文件的同时，并且判断文件是否存在，存在就返回false，否则返回true，同时创建文件
	 */
	private static final Function<File, Tuple2<Boolean, File>> getTuple2 =
			file -> Tuple.of(Try.of(() -> file)
			                    .mapTry(File::exists)
			                    .peek(ConvertUtils.println)
			                    .onFailure(Throwable::printStackTrace)
			                    .get(), file);
	
	
}
