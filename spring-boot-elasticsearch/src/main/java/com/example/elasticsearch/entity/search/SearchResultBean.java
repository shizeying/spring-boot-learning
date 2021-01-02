package com.example.elasticsearch.entity.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果豆
 *
 * @author shizeying
 * @date 2021/01/02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchResultBean implements Serializable {
	private String id;
	private Long tt;
	private String kw;
	private String customQuery;
	private Long rsCount;
	private List<Object> rsData;
	
	/** 聚合的大小 */
	private Integer aggregationSize = 10;
	/**
	 * 搜索纠错词
	 */
	private String recovery = "";
	/**
	 * es查询语句
	 */
	private String searchQuery;
	/**
	 * 同义词集合
	 */
	private List<String> synonymList = new ArrayList<>();
	/**
	 * 是否为敏感词
	 */
	private boolean isSensitive = false;
}
