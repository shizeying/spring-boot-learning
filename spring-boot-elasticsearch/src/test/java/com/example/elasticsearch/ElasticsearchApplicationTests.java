package com.example.elasticsearch;

import com.example.elasticsearch.entity.ExampleEntity;
import com.example.elasticsearch.entity.bo.AttrAgg;
import com.example.elasticsearch.entity.bo.GeneralBuild;
import com.example.elasticsearch.entity.bo.SearchPatternToBuild;
import com.example.elasticsearch.test.PdSegment;
import com.example.elasticsearch.utils.ConvertJavaUtils;
import com.example.elasticsearch.utils.ConvertSearchBuilderUtils;
import com.example.utils.config.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class ElasticsearchApplicationTests {
	
	/**
	 * @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
	 * 		由于AbstractElasticsearchConfiguration中已经注入了elasticsearchOperations和
	 * 		elasticsearchTemplate，本质上这两个函数中的方法并没有什么不同，所以直接使用elasticsearchTemplate
	 * 		就可以了
	 */
	@Autowired
	private ElasticsearchRestTemplate template;
	
	//@Qualifier("org.springframework.data.elasticsearch.core.ElasticsearchOperations")
	
	//TODO 注意：如果查看源码就能看到elasticsearchRestHighLevelClient和elasticsearchClient其实返回类型都是RestHighLevelClient
	// ，这里自己实现了RestClientConfig，所以这里实现elasticsearchClient就可以了
	
	//@Qualifier("elasticsearchClient")
	
	
	@Autowired
	private RestHighLevelClient client;
	final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void contextLoads() {
	
	
	}
	
	@Test
	void setCreateIndex() {
		createIndex();
	}
	
	@Test
	void setHealth() throws IOException {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("mydlq-user");
		Assertions.assertTrue(client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT).isAcknowledged());
		Assertions.assertTrue(createIndex());
		
	}
	
	@Test
	void setTemplate() {
		Assertions.assertTrue(template.indexOps(ExampleEntity.class).delete());
		Assertions.assertTrue(template.indexOps(ExampleEntity.class).create());
		Assertions.assertTrue(template.indexOps(ExampleEntity.class).exists());
	}
	
	@Test
	void setClient() throws IOException {
		final TermsAggregationBuilder aggregation = AggregationBuilders.terms("top_tags")
		                                                               .field("tags")
		                                                               .order(BucketOrder.count(false));
		
		final SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(aggregation);
		final SearchRequest searchRequest = new SearchRequest().indices("blog")
		                                                       .source(builder);
		client.search(searchRequest, RequestOptions.DEFAULT);
		
		
	}
	
	
	/**
	 * 创建索引
	 */
	public boolean createIndex() {
		try {
			// 创建 Mapping
			XContentBuilder mapping = XContentFactory.jsonBuilder()
			                                         .startObject()
			                                         .field("dynamic", true)
			                                         .startObject("properties")
			                                         .startObject("name")
			                                         .field("type", "text")
			                                         .startObject("fields")
			                                         .startObject("keyword")
			                                         .field("type", "keyword")
			                                         .endObject()
			                                         .endObject()
			                                         .endObject()
			                                         .startObject("address")
			                                         .field("type", "text")
			                                         .startObject("fields")
			                                         .startObject("keyword")
			                                         .field("type", "keyword")
			                                         .endObject()
			                                         .endObject()
			                                         .endObject()
			                                         .startObject("remark")
			                                         .field("type", "text")
			                                         .startObject("fields")
			                                         .startObject("keyword")
			                                         .field("type", "keyword")
			                                         .endObject()
			                                         .endObject()
			                                         .endObject()
			                                         .startObject("age")
			                                         .field("type", "integer")
			                                         .endObject()
			                                         .startObject("salary")
			                                         .field("type", "float")
			                                         .endObject()
			                                         .startObject("birthDate")
			                                         .field("type", "date")
			                                         .field("format", "yyyy-MM-dd")
			                                         .endObject()
			                                         .startObject("createTime")
			                                         .field("type", "date")
			                                         .endObject()
			                                         .endObject()
			                                         .endObject();
			// 创建索引配置信息，配置
			Settings settings = Settings.builder()
			                            .put("index.number_of_shards", 1)
			                            .put("index.number_of_replicas", 0)
			                            .build();
			// 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
			CreateIndexRequest request = new CreateIndexRequest("mydlq-user");
			request.settings(settings).mapping(mapping);
			
			
			// RestHighLevelClient 执行创建索引
			CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
			
			// 判断是否创建成功
			return response.isAcknowledged();
			
		} catch (IOException e) {
			log.error("", e);
		}
		return false;
	}
	
	String s = "{\n" +
			           "\"chinese_name\":null,\n" +
			           "\"index_name\":null,\n" +
			           "\"indexes\":[],\n" +
			           "\"fields\": [\n" +
			           "    {\n" +
			           "      \"type\": 1,\n" +
			           "      \"advanced_search\": {\n" +
			           "        \"detail\": {},\n" +
			           "        \"type\": \"0\"\n" +
			           "      },\n" +
			           "      \"speech\": [],\n" +
			           "      \"is_coarse_type\": \"1\",\n" +
			           "      \"is_fine_type\": 1,\n" +
			           "      \"is_fine_grained\": \"1\",\n" +
			           "      \"is_highlight\": false,\n" +
			           "      \"column_item\": [\n" +
			           "        \"attrMap.attrmapa2b6739\"\n" +
			           "      ],\n" +
			           "      \"is_coarse\": false,\n" +
			           "      \"name\": \"数据的卡接收到jj\",\n" +
			           "      \"is_fine\": false,\n" +
			           "      \"aggregations\": {},\n" +
			           "      \"is_show\": true\n" +
			           "    }\n" +
			           "]\n" +
			           "}";
	
	@Test
	void setSearchBean() {
		
		
		SearchPatternToBuild searchPatternToBuild = JacksonUtil.json2BeanByTypeReference(s, new TypeReference<SearchPatternToBuild>() {
		});
		
		System.out.println(JacksonUtil.bean2Json(searchPatternToBuild));
	}
	
	SearchPatternToBuild build = JacksonUtil.json2BeanByTypeReference(s, new TypeReference<SearchPatternToBuild>() {
	});
	
	/**
	 * 构建agg聚合函数
	 */
	@Test
	void setAgg() {
		
		
		build
				.getFields()
				.stream()
				.filter(Objects::nonNull)
				.filter(generalBuild -> Objects.nonNull(generalBuild.getAggregations().getColumn()))
				
				.map(ConvertSearchBuilderUtils::formatGeneralBuild)
				.filter(ConvertJavaUtils.distinctByKey(AggregationBuilder::getName))
				.collect(Collectors.toList());
		
		
	}
	
	/**
	 * 粗排构建
	 */
	@Test
	void thickRowBuild() {
		List<String> words = Arrays.asList("abc");
		BoolQueryBuilder item3 = new BoolQueryBuilder();
		Set<String> columns = build
				                      .getFields()
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
		
		
	}
	
	/**
	 * PdSegments分析构建
	 */
	@Test
	void setPdSegments() {
		List<String> columns = new ArrayList<>();
		List<PdSegment> segmentList = new ArrayList<>();
		BoolQueryBuilder littleIn = QueryBuilders.boolQuery();
		segmentList
				.stream()
				.filter(Objects::nonNull)
				.map(PdSegment::getWord)
				.filter(Objects::nonNull)
				.map(word -> Tuple.of(word, columns))
				.map(ConvertSearchBuilderUtils::formatSegmentBuilder)
				.forEach(littleIn::should);
	}
	
	/**
	 * 构建高亮字段
	 */
	@Test
	void setHighlighter() {
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		build
				.getFields()
				.stream()
				.filter(Objects::nonNull)
				.filter(GeneralBuild::getIsHighlight)
				.map(GeneralBuild::getColumnItem)
				.filter(Objects::nonNull)
				.flatMap(Collection::stream)
				.forEach(highlightBuilder::field);
		
		
	}
	
	/**
	 * 开始搜索
	 */
	@Test
	void setStartSearch() {
		String[] indexes = null;
		QueryBuilder coarseGrainedQueryBuilder = null;
		QueryBuilder fineGrainedQueryBuilder = null;
		
		List<AggregationBuilder> aggregationBuilders = null;
		HighlightBuilder highlightBuilder = null;
		Integer rescorseWindow = null;
		
		String[] index = null;
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
		
		SearchResponse searchResponse = Try.of(() -> client.search(searchRequest, RequestOptions.DEFAULT))
		                                   .onFailure(ConvertSearchBuilderUtils::throwException)
		                                   .get();
		
		
	}
	
	
	/**
	 * 设置结束开始分析聚合
	 */
	@Test
	void setEndStartAnalysisAggregations() {
		SearchResponse searchResponse = null;
		//前期已经构建好了AttrAggK
		List<AttrAgg> attrAggs = Lists.newArrayList();
		List<AttrAgg> collect = attrAggs
				                        .stream()
				                        .filter(Objects::nonNull)
				                        .map(attrAgg -> Tuple.of(searchResponse, attrAgg))
				                        .map(ConvertSearchBuilderUtils::formatAggregationsResult)
				                        .collect(Collectors.toList());
		
		
		if (Objects.isNull(searchResponse)) {
			return;
		}
		
		
	}
	
	
	@Test
	void setEndStartAnalysisResult() {
		SearchResponse searchResponse = null;
		SearchPatternToBuild searchPatternToBuild=new SearchPatternToBuild();
		List<SearchHit> searchHits = Lists.newArrayList(searchResponse.getHits().getHits());
		List<Tuple2<Map<String, String>, Explanation>> lists = searchPatternToBuild
				                                  .getFields()
				                                  .stream()
				                                  .filter(Objects::nonNull)
				                                  .map(generalBuild -> Tuple.of(generalBuild, searchHits, searchPatternToBuild))
				                                  .map(ConvertSearchBuilderUtils::formatSearchHits2Data)
				                                  .filter(Objects::nonNull)
				                                  .collect(Collectors.toList());
	
		
		
	}
	

	
}
