package com.example.elasticsearch.entity.bo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 搜索模式构建
 *
 * @author shizeying
 * @date 2021/01/01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SearchPatternToBuild {
	/** 中文名字 */
	private String chineseName;
	
	/** 索引名称 */
	private String indexName;
	
	/** 多索引模式 */
	private List<Attr> indexes = Lists.newArrayList();
	/** 字段集合 */
	private List<GeneralBuild> fields = Lists.newArrayList();
	
	
}
