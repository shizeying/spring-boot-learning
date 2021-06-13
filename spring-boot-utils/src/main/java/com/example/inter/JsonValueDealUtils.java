package com.example.inter;

import io.vavr.*;

/**
 * json处理接口
 *
 * @author shizeying
 * @date 2021/06/13
 */
public interface JsonValueDealUtils {
	/**
	 * 将数据追加到文件中
	 *
	 * @param tuple3 {@link Tuple3<String, String, String> tuple3}
	 *               <p>
	 *               <blockquote><pre>
	 *                                                                       {@code tuple3._1 filePath}：文件全路径 <p>
	 *                                                                       {@code tuple3._2 content}：追加的内容  <p>
	 *                                                                       {@code tuple3._3 head}：追加文件的请求头，假入该文件是不存在会默认将其写入 <p>
	 *                                                                       </pre></blockquote>
	 *               <p>
	 * @return {@link Tuple3<String, String, String>}
	 * 		<p>
	 * 		<blockquote><pre>
	 *                                                 {@code tuple3._1 filePath}：文件全路径  <p>
	 *                                                 {@code tuple3._2 content}：处理之后的追加的内容     <p>
	 *                                                 {@code tuple3._3 head}：追加文件的请求头，假入该文件是不存在会默认将其写入   <p>
	 * 																											    </pre></blockquote>
	 * 		<p>
	 */
	Tuple3<String, String, String> formatValue(final Tuple3<String, String, String> tuple3);
	
}
