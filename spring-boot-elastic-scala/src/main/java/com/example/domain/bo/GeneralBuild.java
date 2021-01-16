package com.example.domain.bo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 字段模式构建构建
 *
 * @author shizeying
 * @date 2021/01/01
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class GeneralBuild implements Serializable {
	private static final long serialVersionUID = -3004824622398622080L;
	private Integer type;
	
	/**
	 * 字段类型
	 */
	private String esType;
	/**
	 * 显示名称构建
	 */
	private String name;
	/**
	 * 高级搜索构建
	 */
	private AdvancedSearch advancedSearch = new AdvancedSearch();
	
	/** 演讲 */
	private List<Object> speech = Lists.newArrayList();
	/** 是粗糙的类型 0：实体；1：分词 */
	private String isCoarseType = "1";
	/** 是粗 */
	private Boolean isCoarse = true;
	/** 细粒度类型 */
	private Boolean isFine = true;
	/** 细粒度类型： 0：实体；1：分词 */
	private String isFineType = "1";
	/** 细粒度类型 boots 权重分数 */
	private String isFineGrained = "1";
	/** 高亮 */
	private Boolean isHighlight = true;
	private List<String> columnItem = Lists.newArrayList();
	private AggBuilder aggregations = new AggBuilder();
	private Boolean isShow = true;
	
	
}
