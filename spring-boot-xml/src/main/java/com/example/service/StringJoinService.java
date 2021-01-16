package com.example.service;

import com.example.domain.Namespace;

/**
 * 字符串连接服务
 *
 * @author shizeying
 * @date 2020/12/30
 */
public interface StringJoinService {
	/**
	 * 将namespace转换为xml并格式化
	 *
	 * @param namespace
	 * 		名称空间
	 *
	 * @return {@link String}
	 */
	String setAddJoin(Namespace namespace);
	
	
	/**
	 * 写文件
	 *
	 * @param namespace
	 * 		名称空间
	 * @param fileName
	 * 		文件名称，是绝对路径
	 */
	void writeFile(String namespace, String fileName);
	
	
}
