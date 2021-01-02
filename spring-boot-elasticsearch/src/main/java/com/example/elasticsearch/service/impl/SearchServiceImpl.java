package com.example.elasticsearch.service.impl;

import com.example.elasticsearch.entity.bo.Attr;
import com.example.elasticsearch.entity.bo.GeneralBuild;
import com.example.elasticsearch.entity.bo.SearchPatternToBuild;
import com.example.elasticsearch.entity.search.SearchContext;
import com.example.elasticsearch.service.SearchService;
import com.example.elasticsearch.test.PdSegment;
import com.example.elasticsearch.utils.ConvertJavaUtils;
import com.example.elasticsearch.utils.ConvertSearchBuilderUtils;
import com.google.common.collect.Lists;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService {
	@Autowired
	private RestHighLevelClient client;
	
	/**
	 * 格式搜索响应结果
	 *
	 * @param build
	 * 		构建
	 * @param searchResponse
	 */
	@Override
	public void formatSearchResponseResult(SearchPatternToBuild build, SearchResponse searchResponse) {
		List<SearchHit> searchHits = Lists.newArrayList(searchResponse.getHits().getHits());
		List<Tuple2<Map<String, String>, Explanation>> lists = build
				                                                       .getFields()
				                                                       .stream()
				                                                       .filter(Objects::nonNull)
				                                                       .map(generalBuild -> Tuple.of(generalBuild, searchHits, build))
				                                                       .map(ConvertSearchBuilderUtils::formatSearchHits2Data)
				                                                       .filter(Objects::nonNull)
				                                                       .collect(Collectors.toList());
	}
	
	@Override
	public SearchResponse performSearch(String[] index, BoolQueryBuilder coarseGrainedQueryBuilder, BoolQueryBuilder fineGrainedQueryBuilder,
	                                    List<AggregationBuilder> aggregationBuilders, HighlightBuilder highlightBuilder) {
		Integer rescorseWindow = null;
		
		Integer pageNo = null;
		Integer pageSize = null;
		Integer offset = (pageNo - 1) * pageSize;
		Integer size = pageSize;
		String sorts = null;
		Boolean isExplain = null;
		String sortField = null;
		SearchRequest searchRequest = new SearchRequest(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if (Objects.nonNull(coarseGrainedQueryBuilder)) {
			searchSourceBuilder.query(coarseGrainedQueryBuilder);
		}
		if (StringUtils.isBlank(sorts) && Objects.nonNull(fineGrainedQueryBuilder)) {
			searchSourceBuilder.addRescorer(new QueryRescorerBuilder(fineGrainedQueryBuilder).windowSize(rescorseWindow));
			
		}
		aggregationBuilders
				.stream()
				.forEach(searchSourceBuilder::aggregation);
		if (Objects.nonNull(highlightBuilder)) {
			searchSourceBuilder.highlighter(highlightBuilder);
		}
		if (isExplain && pageSize != 100) {
			searchSourceBuilder.explain(true);
		}
		searchSourceBuilder.from(offset).size(size);
		if (StringUtils.isNotBlank(sorts)) {
			switch (sorts) {
				//升序
				case "0":
					searchSourceBuilder.sort(sortField, SortOrder.ASC);
					break;
				//降序
				case "1":
					searchSourceBuilder.sort(sortField, SortOrder.DESC);
					break;
				default:
					throw new NullPointerException("排序参数异常");
			}
		}
		searchRequest.source(searchSourceBuilder);
		
		return Try.of(() -> client.search(searchRequest, RequestOptions.DEFAULT))
		          .onFailure(ConvertSearchBuilderUtils::throwException)
		          .get();
		
	}
	
	@Override
	public void searchFirstBuild(SearchContext searchContext) {
		//根据名称获取SearchPatternToBuild
		
		
		
		SearchPatternToBuild build = null;
		List<String> words = new ArrayList<>();
		String[] index = null;
		//获取索引
		if (build.getIndexes().isEmpty()) {
			index = new String[]{build.getIndexName()};
			
		} else {
			index = build
					        .getIndexes()
					        .stream()
					        .map(Attr::getIndexPrefix)
					        .map(s -> s + "*")
					        .toArray(String[]::new);
			
			
		}
		
		//敏感词过滤
		
		//热词补充
		
		//从redis获取缓存结果
		
		
		//判断kw和高级
		
		
		//热词保存
		
		//粗排构建
		BoolQueryBuilder coarseGrainedQueryBuilder = fuzzySearchBuild(build.getFields(), words);
		//精排构建
		BoolQueryBuilder fineGrainedQueryBuilder=null;
		//高亮构建
		HighlightBuilder highlightBuilder = highlightingBuild(build.getFields());
		//聚合函数构建
		List<AggregationBuilder> aggregationBuilders = aggregateFunctionsToBuild(build.getFields());
		//开始搜索
		SearchResponse searchResponse = performSearch(index, coarseGrainedQueryBuilder, fineGrainedQueryBuilder, aggregationBuilders,
				highlightBuilder);
		//处理返回结果
		formatSearchResponseResult(build,searchResponse);
		//保存redis
		
		//同义词提取新集合返回，并将纠错词从分词中回退
		
		
		
	}
	
	/**
	 * 强调建立
	 *
	 * @param fields
	 * 		字段
	 *
	 * @return
	 */
	@Override
	public HighlightBuilder highlightingBuild(List<GeneralBuild> fields) {
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		fields
				.stream()
				.filter(Objects::nonNull)
				.filter(GeneralBuild::getIsHighlight)
				.map(GeneralBuild::getColumnItem)
				.filter(Objects::nonNull)
				.flatMap(Collection::stream)
				.forEach(highlightBuilder::field);
		return highlightBuilder;
	}
	
	/**
	 * 聚合函数构建 @param fields 字段
	 *
	 * @param fields
	 *
	 * @return
	 */
	@Override
	public List<AggregationBuilder> aggregateFunctionsToBuild(List<GeneralBuild> fields) {
		return fields
				       .stream()
				       .filter(Objects::nonNull)
				       .filter(generalBuild -> Objects.nonNull(generalBuild.getAggregations().getColumn()))
				
				       .map(ConvertSearchBuilderUtils::formatGeneralBuild)
				       .filter(ConvertJavaUtils.distinctByKey(AggregationBuilder::getName))
				       .collect(Collectors.toList())
				
				
				;
	}
	
	@Override
	public BoolQueryBuilder getMatchPhraseQuery(List<String> columns, List<PdSegment> segmentList) {
		BoolQueryBuilder littleIn = QueryBuilders.boolQuery();
		segmentList
				.stream()
				.filter(Objects::nonNull)
				.map(PdSegment::getWord)
				.filter(Objects::nonNull)
				.map(word -> Tuple.of(word, columns))
				.map(ConvertSearchBuilderUtils::formatSegmentBuilder)
				.forEach(littleIn::should);
		return littleIn;
	}
	
	@Override
	public BoolQueryBuilder fuzzySearchBuild(List<GeneralBuild> fields, List<String> words) {
		List<BoolQueryBuilder> queryBuilders = Lists.newArrayList();
		// PdSegment构建
		List<PdSegment> segmentList = null;
		
		
		//bool构建
		BoolQueryBuilder item3 = new BoolQueryBuilder();
		Set<String> columns = fields
				                      .stream()
				                      .filter(Objects::nonNull)
				                      .filter(GeneralBuild::getIsCoarse)
				                      .map(GeneralBuild::getColumnItem)
				                      .flatMap(Collection::stream)
				                      .collect(Collectors.toSet());
		if (words.isEmpty() || columns.isEmpty()) {
			words
					.stream()
					.filter(Objects::nonNull)
					.map(word ->
							     columns
									     .stream()
									     .map(column -> QueryBuilders.termQuery(column, word))
									     .collect(Collectors.toList())
					
					
					)
					.flatMap(Collection::stream)
					.forEach(item3::should);
		}
		BoolQueryBuilder matchPhraseQuery = getMatchPhraseQuery(Lists.newArrayList(columns), segmentList);
		item3.should(matchPhraseQuery);
		return item3;
		
	}
}
