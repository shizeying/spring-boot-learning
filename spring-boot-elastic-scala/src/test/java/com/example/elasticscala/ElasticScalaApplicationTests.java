package com.example.elasticscala;

import com.example.config.BuildPrefix;
import com.example.config.EsUtils;
import com.example.domain.bo.GeneralBuild;
import com.example.domain.bo.SearchPatternToBuild;
import com.example.service.TConfigService;
import com.example.utils.*;
import com.google.common.collect.Lists;
import com.sksamuel.elastic4s.ElasticClient;
import com.sksamuel.elastic4s.requests.searches.HighlightField;
import com.sksamuel.elastic4s.requests.searches.SearchRequest;
import com.sksamuel.elastic4s.requests.searches.SearchResponse;
import com.sksamuel.elastic4s.requests.searches.aggs.*;
import com.sksamuel.elastic4s.requests.searches.aggs.responses.Aggregations;
import com.sksamuel.elastic4s.requests.searches.queries.BoolQuery;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scala.runtime.BoxedUnit;

import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@SpringBootTest
class ElasticScalaApplicationTests {
	@Autowired
	private ElasticClient client;
	@Autowired
	private TConfigService tConfigService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void setService() {
		String kw = "名称";
		SearchPatternToBuild build = tConfigService.findByChineseName("普通关系");
		String indexName = build.getIndexName() + "*";
		List<HighlightEntity> highlightEntities = build
				                                          .getFields()
				                                          .stream()
				                                          .filter(Objects::nonNull)
				                                          .filter(generalBuild -> Objects.nonNull(generalBuild.getIsHighlight()))
				                                          .filter(GeneralBuild::getIsHighlight)
				                                          .map(GeneralBuild::getColumnItem)
				                                          .flatMap(Collection::stream)
				                                          .map(column -> new HighlightEntity(column, null))
				                                          .collect(Collectors.toList());
		
		
		List<TermEntity> termEntities = build
				                                .getFields()
				                                .stream()
				                                .filter(generalBuild -> Objects.nonNull(generalBuild.getEsType()))
				                                .map(generalBuild -> generalBuild
						                                                     .getColumnItem()
						                                                     .stream()
						                                                     .filter(Objects::nonNull)
						                                                     .map(column -> new TermEntity(column, Arrays.asList(kw), null))
						                                                     .collect(Collectors.toList())
				                                ).flatMap(Collection::stream)
				                                .collect(Collectors.toList());
		
		List<AttrAggK> attrAggs = build
				                          .getFields()
				                          .stream()
				                          .filter(generalBuild -> Objects.nonNull(generalBuild.getAggregations().getColumn()))
				                          .map(this::formatAttrAgg)
				                          .map(AttrAgg::k)
				                          .collect(Collectors.toList());
		
		BoolQuery boolQuery = SearchUtils.builderFineTermsQuery(termEntities);
		BoolQuery boolQueryTerms = SearchUtils.builderFineTermsQuery(termEntities);
		SearchUtils.builderTermQueries(termEntities);
		List<HighlightField> highlightFields = SearchUtils.builderHighlight(highlightEntities);
		List<AutoDateHistogramAggregation> aggOne = AggUtils.builderAutoIntervalDateAgg(attrAggs);
		List<DateHistogramAggregation> aggTwo = AggUtils.builderDateHistogramAgg(attrAggs);
		List<SigTermsAggregation> aggThree = AggUtils.builderSignificantTextAgg(attrAggs);
		List<SigTermsAggregation> aggFour = AggUtils.builderSignificantTextAgg(attrAggs);
		List<TermsAggregation> aggFive = AggUtils.builderTermsAgg(attrAggs);
		
		List<AbstractAggregation> abstractAggregations = Lists.newArrayList();
		abstractAggregations.addAll(aggOne);
		abstractAggregations.addAll(aggTwo);
		abstractAggregations.addAll(aggThree);
		abstractAggregations.addAll(aggFour);
		abstractAggregations.addAll(aggFive);
		abstractAggregations.addAll(aggTwo);
		abstractAggregations.addAll(aggTwo);
	
		SearchRequest searchRequest = EsUtils.buildSearchRequest(Arrays.asList(indexName), highlightFields,
				abstractAggregations, boolQueryTerms, boolQueryTerms,
				null	);
		SearchResponse searchResponse = EsUtils.performSearch(searchRequest, client);
		Aggregations aggregations = searchResponse.aggregations();
		List<AttrAgg> attrAgges = build
				                          .getFields()
				                          .stream()
				                          .filter(generalBuild -> Objects.nonNull(generalBuild.getAggregations().getColumn()))
				                          .map(this::formatAttrAgg)
				                          .collect(Collectors.toList());
		List<AttrAgg> apply = AttrAgg.apply(aggregations, attrAgges);
		
		List<HitResult> apply1 = HitResult.apply(searchResponse.hits());
		
		//System.err.println(EsUtils.printJson(apply1));
		System.out.println("apply1.size():"+apply1.size());
		
	}
	
	private AttrAgg formatAttrAgg(GeneralBuild generalBuild) {
		String name = generalBuild.getName();
		String column = generalBuild.getAggregations().getColumn();
		String format = generalBuild.getAggregations().getFormat();
		String interval = generalBuild.getAggregations().getInterval();
		String size = generalBuild.getAggregations().getSize();
		List<String> include = Objects.nonNull(generalBuild.getAggregations().getInclude()) ? generalBuild.getAggregations()
		                                                                                                  .getInclude() : Lists.newArrayList();
		List<String> detail = Objects.nonNull(generalBuild.getAggregations().getDetail()) ? generalBuild.getAggregations()
		                                                                                                .getDetail() : Lists.newArrayList();
		List<String> exclude = Objects.nonNull(generalBuild.getAggregations().getExclude()) ? generalBuild.getAggregations()
		                                                                                                  .getExclude() : Lists.newArrayList();
		String type = generalBuild.getAggregations().getType();
		String esType = generalBuild.getEsType();
		AttrAggK attrAggK = new AttrAggK(name, column, esType,
				interval, format, include, exclude,
				type, Integer.parseInt(size), null, null, detail);
		
		String d = null;
		switch (type) {
			case "terms":
				d = "0";
				break;
			case "range":
				d = "1";
				break;
			case "interval":
				d = "2";
				break;
			default:
				throw new NullPointerException("聚合类型发生错误");
		}
		
		
		return new AttrAgg(attrAggK, Lists.newArrayList(), d);
	}
	
	/**
	 * 设置通用搜索
	 */
	@Test
	void setGenericSearch() {
	
	
	}
	
	@Test
	void setCoarse() {
		SearchPatternToBuild build = tConfigService.findByChineseName("普通关系");
		String indexName = build.getIndexName() + "*";
		String kw = "我要查询这个名称记录关联基站第一接收地点2020-11-11";
		List<String> wordList = Lists.newArrayList();
		if (RegexpRuleUtils.checkRegexpRule(kw)) {
			wordList.add(kw);
		}
		List<String> wordStart =Lists.newArrayList();
		List<String> hanlpKw = SearchUtils.segmentKw(kw);
		wordStart.addAll( SearchUtils.segmentKwOverload(kw));
		wordStart.addAll(hanlpKw);
		List<String> words = wordStart.stream().distinct().collect(Collectors.toList());
		
		List<String> wordsFive = words.stream()
		                              .filter(word -> word.length() <= 5)
		                              .collect(Collectors.toList());
		
		List<String> wordsDate = words.stream()
		                              .filter(RegexpUtils::checkDate)
		                              .map(RegexpUtils::getIsDate)
		                              .collect(Collectors.toList());
		
		words.addAll(wordsDate);
		
		
		
		List<BoolQuery> boolQueries = new ArrayList<>();
		List<GeneralBuild> fieldCoarse = build
				                                 .getFields()
				                                 .stream()
				                                 .filter(Objects::nonNull)
				                                 .filter(GeneralBuild::getIsCoarse)
				                                 .filter(generalBuild -> StringUtils.isNotBlank(generalBuild.getEsType()))
				                                 .filter(generalBuild -> StringUtils.isNotBlank(generalBuild.getIsCoarseType()))
				                                 .collect(Collectors.toList());
		//触发正则
		if (!wordList.isEmpty()) {
			List<TermEntity> termsEntitiesReg = fieldCoarse
					                                    .stream()
					                                    .map(generalBuild ->
							                                         generalBuild
									                                         .getColumnItem()
									                                         .stream()
									                                         .filter(Objects::nonNull)
									                                         .map(column -> new TermEntity(column, wordList, null))
									                                         .collect(Collectors.toList())
					                                    )
					                                    .flatMap(Collection::stream)
					                                    .collect(Collectors.toList());
			
			BoolQuery boolQuery = SearchUtils.builderRegexpQuery(termsEntitiesReg);
			boolQueries.add(boolQuery);
		}
		//通用
		if (!words.isEmpty()) {
			List<TermEntity> termEntitiesGen = fieldCoarse
					                                   .stream()
					                                   .filter(generalBuild -> StringUtils.equalsAnyIgnoreCase(generalBuild.getEsType(),"text",
							                                   "keyword"))
					                                   .map(generalBuild ->
							                                        generalBuild
									                                        .getColumnItem()
									                                        .stream()
									                                        .filter(Objects::nonNull)
									                                        .map(column -> new TermEntity(column, words, null))
									                                        .collect(Collectors.toList())
					                                   )
					                                   .flatMap(Collection::stream)
					                                   .collect(Collectors.toList());
			
			
			BoolQuery boolQuery = SearchUtils.builderFuzzyQuery(termEntitiesGen);
			boolQueries.add(boolQuery);
		}
		//2-5 的时候触发通配符和前缀匹配   短语
		if (!wordsFive.isEmpty()) {
			List<TermEntity> termEntities2to5 = fieldCoarse
					                                    .stream()
					                                    .filter(generalBuild -> !StringUtils.equalsAnyIgnoreCase(generalBuild.getEsType(),
							                                    "date"))
					                                    .map(generalBuild ->
							                                         generalBuild
									                                         .getColumnItem()
									                                         .stream()
									                                         .filter(Objects::nonNull)
									                                         .map(column -> new TermEntity(column, words, null))
									                                         .collect(Collectors.toList())
					                                    )
					                                    .flatMap(Collection::stream)
					                                    .collect(Collectors.toList());
			List<TermEntity> termEntities2to5Text = fieldCoarse
					                                    .stream()
					                                    .filter(generalBuild -> StringUtils.equalsAnyIgnoreCase(generalBuild.getEsType(),"text"))
					                                    .map(generalBuild ->
							                                         generalBuild
									                                         .getColumnItem()
									                                         .stream()
									                                         .filter(Objects::nonNull)
									                                         .map(column -> new TermEntity(column, words, null))
									                                         .collect(Collectors.toList())
					                                    )
					                                    .flatMap(Collection::stream)
					                                    .collect(Collectors.toList());
			List<TermEntity> termEntities2to5date = fieldCoarse
					                                    .stream()
					                                    .filter(generalBuild -> StringUtils.equalsAnyIgnoreCase(generalBuild.getEsType(),"text",
							                                    "keyword"))
					                                    .map(generalBuild ->
							                                         generalBuild
									                                         .getColumnItem()
									                                         .stream()
									                                         .filter(Objects::nonNull)
									                                         .map(column -> new TermEntity(column, words, null))
									                                         .collect(Collectors.toList())
					                                    )
					                                    .flatMap(Collection::stream)
					                                    .collect(Collectors.toList());
			BoolQuery boolQueryWildcard = SearchUtils.builderWildcardQuery(termEntities2to5date);
			boolQueries.add(boolQueryWildcard);
			
			boolQueries.add(SearchUtils.builderMatchPhrasePrefixQuery(termEntities2to5Text));
			BoolQuery boolQuery = SearchUtils.builderMatchPhraseQuery(termEntities2to5);
			boolQueries.add(boolQuery);
			
		}
		
		BoolQuery coarseGrainedQueryBuilder = SearchUtils.convertBoolQuery(boolQueries, null);
	
		//高亮构建
		List<HighlightEntity> highlightEntities = build
				                                          .getFields()
				                                          .stream()
				                                          .filter(Objects::nonNull)
				                                          .filter(generalBuild -> Objects.nonNull(generalBuild.getIsHighlight()))
				                                          .filter(GeneralBuild::getIsHighlight)
				                                          .map(GeneralBuild::getColumnItem)
				                                          .flatMap(Collection::stream)
				                                          .map(column -> new HighlightEntity(column, null))
				                                          .collect(Collectors.toList());
		List<AttrAggK> attrAggs = build
				                          .getFields()
				                          .stream()
				                          .filter(generalBuild -> Objects.nonNull(generalBuild.getAggregations().getColumn()))
				                          .map(this::formatAttrAgg)
				                          .map(AttrAgg::k)
				                          .collect(Collectors.toList());
		
		
		List<HighlightField> highlightFields = SearchUtils.builderHighlight(highlightEntities);
		List<AutoDateHistogramAggregation> aggOne = AggUtils.builderAutoIntervalDateAgg(attrAggs);
		List<DateHistogramAggregation> aggTwo = AggUtils.builderDateHistogramAgg(attrAggs);
		List<SigTermsAggregation> aggThree = AggUtils.builderSignificantTextAgg(attrAggs);
		List<SigTermsAggregation> aggFour = AggUtils.builderSignificantTextAgg(attrAggs);
		List<TermsAggregation> aggFive = AggUtils.builderTermsAgg(attrAggs);
		
		List<AbstractAggregation> abstractAggregations = Lists.newArrayList();
		abstractAggregations.addAll(aggOne);
		abstractAggregations.addAll(aggTwo);
		abstractAggregations.addAll(aggThree);
		abstractAggregations.addAll(aggFour);
		abstractAggregations.addAll(aggFive);
		abstractAggregations.addAll(aggTwo);
		abstractAggregations.addAll(aggTwo);
		SearchRequest searchRequest = EsUtils.buildSearchRequest(Arrays.asList(indexName), highlightFields,
				abstractAggregations, coarseGrainedQueryBuilder, null,
				new BuildPrefix(null, null, 10, 1, true));
	
		
		SearchResponse searchResponse = EsUtils.performSearch(searchRequest, client);
		Aggregations aggregations = searchResponse.aggregations();
		List<AttrAgg> attrAgges = build
				                          .getFields()
				                          .stream()
				                          .filter(generalBuild -> Objects.nonNull(generalBuild.getAggregations().getColumn()))
				                          .map(this::formatAttrAgg)
				                          .collect(Collectors.toList());
		List<AttrAgg> apply = AttrAgg.apply(aggregations, attrAgges);
		
		List<HitResult> apply1 = HitResult.apply(searchResponse.hits());
		System.out.println(EsUtils.printJson(apply1));


		
	}
	
	/**
	 * 设置精确搜索
	 */
	@Test
	void setFineSearch() {
		String kw = "手机号";
		List<String> words = SearchUtils.segmentKwOverload(kw);
		SearchPatternToBuild build = tConfigService.findByChineseName("普通关系");
		String indexName = build.getIndexName() + "*";
		List<TermEntity> termEntityFine = build
				                                  .getFields()
				                                  .stream()
				                                  .filter(Objects::nonNull)
				                                  .filter(GeneralBuild::getIsFine)
				                                  .filter(generalBuild -> StringUtils.isNotBlank(generalBuild.getEsType()))
				                                  .filter(generalBuild -> StringUtils.isNotBlank(generalBuild.getIsFineGrained()))
				                                  .map(generalBuild ->
						                                       generalBuild
								                                       .getColumnItem()
								                                       .stream()
								                                       .filter(Objects::nonNull)
								                                       .map(column -> new TermEntity(column, words,
										                                       Integer.parseInt(generalBuild.getIsFineGrained())))
								                                       .collect(Collectors.toList())
				                                  )
				                                  .flatMap(Collection::stream)
				                                  .collect(Collectors.toList());
		
		
	}
	
	/**
	 * 设置高亮实体
	 */
	@Test
	void setHighlightEntity() {
		SearchPatternToBuild build = tConfigService.findByChineseName("普通关系");
		String indexName = build.getIndexName() + "*";
		List<HighlightEntity> highlightEntities = build
				                                          .getFields()
				                                          .stream()
				                                          .filter(Objects::nonNull)
				                                          .filter(generalBuild -> Objects.nonNull(generalBuild.getIsHighlight()))
				                                          .filter(GeneralBuild::getIsHighlight)
				                                          .map(GeneralBuild::getColumnItem)
				                                          .flatMap(Collection::stream)
				                                          .map(column -> new HighlightEntity(column, null))
				                                          .collect(Collectors.toList());
	}
	
}
