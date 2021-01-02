package com.example.elasticsearch.service;

import com.example.elasticsearch.entity.bo.GeneralBuild;
import com.example.elasticsearch.entity.bo.SearchPatternToBuild;
import com.example.elasticsearch.entity.search.SearchContext;
import com.example.elasticsearch.test.PdSegment;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.util.List;

/**
 * 搜索服务
 *
 * @author shizeying
 * @date 2021/01/02
 */
public interface SearchService {
	
	/**
	 * 搜索第一个构建
	 *
	 * @param searchContext
	 * 		中文名字
	 */
	void  searchFirstBuild(SearchContext searchContext);
	
	/** 模糊搜索构建
	 * @return*/
	BoolQueryBuilder fuzzySearchBuild(List<GeneralBuild> fields, List<String> words);
	
	/**
	 * 匹配短语查询
	 *
	 * @param columns
	 * 		列
	 * @param segmentList
	 * 		部分列表
	 *
	 * @return {@link BoolQueryBuilder}
	 */
	BoolQueryBuilder getMatchPhraseQuery(List<String> columns, List<PdSegment> segmentList);
	
	
	/**
	 * 强调建立
	 *
	 * @param fields
	 * 		字段
	 * @return
	 */
	HighlightBuilder highlightingBuild(List<GeneralBuild> fields);
	
	/**
	 * 聚合函数构建 @param fields 字段
	 * @return
	 */
	List<AggregationBuilder> aggregateFunctionsToBuild(List<GeneralBuild> fields);
	
	
	/**
	 * 执行搜索
	 *  @param index
	 * 		指数
	 * @param coarseGrainedQueryBuilder
	 * 		粗粒度的查询生成器
	 * @param fineGrainedQueryBuilder
 * 		细粒查询构建器
	 * @param aggregationBuilders
* 		聚合建筑
	 * @param highlightBuilder
	 * @return
	 */
	SearchResponse performSearch(String[] index, BoolQueryBuilder coarseGrainedQueryBuilder, BoolQueryBuilder fineGrainedQueryBuilder,
	                             List<AggregationBuilder>	aggregationBuilders, HighlightBuilder highlightBuilder);
	
	
	/**
	 * 格式搜索响应结果
	 *
	 * @param build
	 * 		构建
	 * @param searchResponse
	 * 		搜索响应
	 */
	void formatSearchResponseResult(SearchPatternToBuild build,SearchResponse searchResponse);
	
}
