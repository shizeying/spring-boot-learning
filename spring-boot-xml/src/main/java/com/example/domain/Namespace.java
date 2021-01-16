package com.example.domain;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 名称空间
 *
 * @author shizeying
 * @date 2020/12/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("ALL")
public class Namespace {
	
	
	final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			                      "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";
	
	final String end = "</mapper>";
	final String baseMapEnd = "</resultMap>";
	private String namespace;
	private String type;
	private String mapper;
	private List<XmlBean> xmlBeans = Lists.newArrayList();
	private List<SqlBean> sqlBeans = Lists.newArrayList();
	
	
	@Override
	public String toString() {
		
		return header +
				       "<mapper namespace=\"" + StringUtils.trim(mapper) + "\" >"
				       +
				       "\n" + "<resultMap id=\"BaseResultMap\" type=\"" + StringUtils.trim(type) + "\">"
				
				
				       +
				
				       "\n" + xmlBeans
						              .stream()
						              .map(XmlBean::toString)
						              .collect(Collectors.joining("\n"))
				       +
				       "\n" + baseMapEnd +
				
				       "\n" + sqlBeans
						              .stream()
						              .map(SqlBean::toString)
						              .collect(Collectors.joining("\n")
						              )
				       + end;
		
		
	}
	
	
}
