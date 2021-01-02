package com.example.elasticsearch.utils;

import com.example.elasticsearch.entity.bo.*;
import com.example.utils.config.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.vavr.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.IncludeExclude;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 将搜索转换
 *
 * @author shizeying
 * @date 2021/01/01
 */
public final class ConvertSearchBuilderUtils {
	
	private ConvertSearchBuilderUtils() {
	}
	
	/**
	 * 聚合构建
	 *
	 * @param general
	 * 		一般
	 *
	 * @return {@link AggregationBuilder}
	 */
	public static AggregationBuilder formatGeneralBuild(GeneralBuild general) {
		String name = "key" + general.getName();
		String column = StringUtils.contains(general.getAggregations().getColumn(), ".keyword") ? general.getAggregations().getColumn() :
				                general.getAggregations().getColumn() + ".keyword";
		Integer aggSize = Objects.isNull(general.getAggregations().getSize()) ? 30 : general.getAggregations().getSize();
		switch (general.getAggregations().getType()) {
			
			case 0:
				
				TermsAggregationBuilder termsAggregator =
						AggregationBuilders.terms(name)
						                   .field(column)
						                   .size(aggSize);
				if (!general.getAggregations().getExclude().isEmpty() || general.getAggregations().getInclude().isEmpty()) {
					termsAggregator.includeExclude(new IncludeExclude(general.getAggregations().getExclude().toArray(new String[0]),
							general.getAggregations().getInclude().toArray(new String[0])));
				}
				return termsAggregator;
			
			//// 范围聚合
			case 1:
				// 范围聚合
				String startTime = "";
				String endTime = "";
				return
						AggregationBuilders.range(name).field(general.getAggregations().getColumn())
						                   .addRange(
								                   startTime + "" + endTime,
								                   Double.parseDouble(startTime),
								                   Double.parseDouble(endTime));
			
			
			case 2:
				// interval聚合
				double intervals = 0d;
				return
						AggregationBuilders.histogram(name)
						                   .field(general.getAggregations().getColumn())
						                   .interval(intervals);
			case 3:
				// interval聚合 时间
				String inter = "";
				String format = "";
				return
						AggregationBuilders.dateHistogram(name)
						                   .field(general.getAggregations().getColumn())
						                   .calendarInterval(getDateHistogramInterval(inter))
						                   .format(format)
						                   .order(BucketOrder.key(false));
			
			
			default:
				throw new NullPointerException("期待更多的加入");
		}
	}
	
	private static DateHistogramInterval getDateHistogramInterval(String interval) {
		
		switch (interval) {
			case "seconds":
				return DateHistogramInterval.SECOND;
			case "minute":
				return DateHistogramInterval.MINUTE;
			case "hour":
				return DateHistogramInterval.HOUR;
			case "day":
				return DateHistogramInterval.DAY;
			case "week":
				return DateHistogramInterval.WEEK;
			case "month":
				return DateHistogramInterval.MONTH;
			case "quarter":
				return DateHistogramInterval.QUARTER;
			case "year":
				return DateHistogramInterval.YEAR;
			default:
				throw new NullPointerException("interval 传入有问题");
		}
		
	}
	
	
	/**
	 * 格式部分建设者
	 * 对pdSegment进行搜索构建
	 *
	 * @param wordTextAndColumns
	 * 		词的文本和列
	 *
	 * @return {@link ConstantScoreQueryBuilder}
	 */
	public static ConstantScoreQueryBuilder formatSegmentBuilder(Tuple2<String, List<String>> wordTextAndColumns) {
		BoolQueryBuilder segmentMust = QueryBuilders.boolQuery().minimumShouldMatch(1);
		String wordText = wordTextAndColumns._1;
		wordTextAndColumns._2
				.stream()
				.filter(Objects::nonNull)
				.map(column -> QueryBuilders.matchPhraseQuery(column, wordText))
				.forEach(segmentMust::should);
		
		return QueryBuilders.constantScoreQuery(segmentMust).boost(1);
	}
	
	public static void throwException(Throwable throwable) {
		throwable.printStackTrace();
	}
	
	
	public static AttrAgg formatAggregationsResult(Tuple2<SearchResponse, AttrAgg> tuple2) {
		String type = tuple2._2.getD();
		String name = "key" + tuple2._2.getK().getName();
		SearchResponse searchResponse = tuple2._1;
		List<AttrAggV> attrAggVList = Lists.newArrayList();
		switch (type) {
			case "0":
				// 单值
				(((Terms) searchResponse.getAggregations().getAsMap().get(name))
						 .getBuckets())
						.stream()
						.filter(Objects::nonNull)
						.map(bucket -> Tuple.of(bucket, tuple2._2.getV()))
						.map(ConvertSearchBuilderUtils::formatV0)
						.forEach(attrAggVList::addAll);
				
				break;
			case "1":
				
				(((Range) searchResponse.getAggregations().getAsMap().get(name))
						 .getBuckets())
						.stream()
						.filter(Objects::nonNull)
						.map(bucket -> Tuple.of(bucket, tuple2._2.getV()))
						.map(ConvertSearchBuilderUtils::formatV1)
						.forEach(attrAggVList::addAll);
				break;
			
			case "2":
				(((Histogram) searchResponse.getAggregations().getAsMap().get(name))
						 .getBuckets())
						.stream()
						.filter(Objects::nonNull)
						.map(ConvertSearchBuilderUtils::formatV2)
						.forEach(attrAggVList::add);
				break;
			case "3":
				((ParsedDateHistogram)
						 searchResponse.getAggregations().getAsMap().get(name))
						.getBuckets()
						.stream()
						.filter(Objects::nonNull)
						.map(bucket -> Tuple.of(bucket, tuple2._2))
						.map(ConvertSearchBuilderUtils::formatV3)
						.forEach(attrAggVList::add);
				break;
			default:
				throw new NullPointerException("转换错误");
			
			
		}
		tuple2._2.setV(attrAggVList);
		
		return tuple2._2;
	}
	
	private static AttrAggV formatV3(Tuple2<? extends Histogram.Bucket, AttrAgg> tuple2) {
		return AttrAggV
				       .builder()
				
				       .key(formatDateTime((ZonedDateTime) tuple2._1.getKey(), tuple2._2.getK().getFormat()))
				       .value(formatDateTime((ZonedDateTime) tuple2._1.getKey(), tuple2._2.getK().getFormat()))
				       .aggSum(tuple2._1.getDocCount())
				       .build()
				;
	}
	
	
	private static AttrAggV formatV2(Histogram.Bucket bucket) {
		return AttrAggV
				       .builder()
				
				       .key(bucket.getKey())
				       .value(bucket.getKey())
				       .aggSum(bucket.getDocCount())
				       .build()
				;
	}
	
	
	private static List<AttrAggV> formatV1(Tuple2<? extends Range.Bucket, List<AttrAggV>> tuple2) {
		Range.Bucket bucket = tuple2._1;
		String key = tuple2._1.getKey() + "";
		
		return tuple2._2
				       .stream()
				       .filter(Objects::nonNull)
				       .map(attrAggV -> Tuple.of(key, attrAggV))
				       .filter(ConvertSearchBuilderUtils::filterRange)
				       .map(Tuple2::_2)
				       .map(attrAggV -> attrAggV.setAggSum(bucket.getDocCount()))
				       .collect(Collectors.toList());
		
		
	}
	
	/**
	 * 过滤范围
	 *
	 * @param tuple2
	 * 		tuple2
	 *
	 * @return boolean
	 */
	private static boolean filterRange(Tuple2<String, AttrAggV> tuple2) {
		Long[] key = JacksonUtil.json2BeanByTypeReference(tuple2._2().getKey() + "", new TypeReference<Long[]>() {
		});
		return (key[0] + "" + key[1]).equals(tuple2._1);
	}
	
	private static List<AttrAggV> formatV0(Tuple2<? extends Terms.Bucket, List<AttrAggV>> tuple2) {
		Terms.Bucket bucket = tuple2._1;
		String key = tuple2._1.getKey() + "";
		List<AttrAggV> attrAggVS = tuple2._2;
		List<AttrAggV> newAttrAggVs = Lists.newArrayList();
		AttrAggV v = AttrAggV
				             .builder()
				             .key(key)
				             .value(key)
				             .aggSum(bucket.getDocCount())
				             .build();
		if (Objects.isNull(attrAggVS) || attrAggVS.size() <= 0) {
			
			newAttrAggVs.add(v);
			
		} else if (Objects.nonNull(attrAggVS)) {
			
			tuple2._2
					.stream()
					.filter(Objects::nonNull)
					.filter(attrAggV -> attrAggV.getKey().equals(key))
					.map(attrAggV -> attrAggV.setAggSum(bucket.getDocCount()))
					.forEach(newAttrAggVs::add);
			long count = tuple2._2
					             .stream()
					             .filter(Objects::nonNull)
					             .filter(attrAggV -> !attrAggV.getKey().equals(key))
					             .count();
			if (count > 0) {
				newAttrAggVs.add(v);
			}
			
		}
		newAttrAggVs.addAll(attrAggVS);
		
		
		return newAttrAggVs.stream().filter(ConvertJavaUtils.distinctByKey(AttrAggV::getKey)).collect(Collectors.toList());
	}
	
	private static Object formatDateTime(ZonedDateTime key, String format) {
		return key.format(DateTimeFormatter.ofPattern(format));
	}
	
	/**
	 * SearchHit进行查询匹配转换
	 *
	 * @param tuple2
	 * 		tuple2
	 *
	 * @return {@link Map<String, String>}
	 */
	public static  Tuple2<Map<String, String>, Explanation> formatSearchHits2Data(Tuple3<GeneralBuild, List<SearchHit>, SearchPatternToBuild> tuple2) {
		GeneralBuild generalBuild = tuple2._1;
		return generalBuild.getColumnItem()
		                   .stream()
		                   .filter(Objects::nonNull)
		                   .map(column -> Tuple.of(column, tuple2._2, tuple2._3, generalBuild))
		                   //对每个column进行值匹配
		                   .map(ConvertSearchBuilderUtils::singleColumnFormat)
		                   .filter(Objects::nonNull)
		                   .findAny()
		                   .orElse(null);
		
	}
	
	
	/**
	 * 单栏格式
	 *
	 * @param tuple2
	 * 		Tuple4<String, List<SearchHit>, SearchPatternToBuild, GeneralBuild>   column   SearchHits   SearchPatternToBuild, GeneralBuild
	 *
	 * @return {@link Map<String, String>}
	 */
	private static  Tuple2<Map<String, String>, Explanation>  singleColumnFormat(Tuple4<String, List<SearchHit>, SearchPatternToBuild, GeneralBuild> tuple2) {
		
		
		return tuple2._2
				       .stream()
				       .filter(Objects::nonNull)
				       .map(hit -> Tuple.of(tuple2._1, hit, tuple2._3, tuple2._4))
				       //对返回的值进行高亮设置
				       .map(ConvertSearchBuilderUtils::formatHighlightData)
				       .filter(Objects::nonNull)
				       .findAny()
				       .orElse(null);
		
		
	}
	
	/**
	 * 强调数据格式
	 *
	 * @param tuple2
	 * 		Tuple4<String, SearchHit, SearchPatternToBuild, GeneralBuild>   column    SearchHit SearchPatternToBuild, GeneralBuild
	 *
	 * @return {@link Map<String, String>}
	 */
	private static Tuple2<Map<String, String>, Explanation> formatHighlightData(Tuple4<String, SearchHit, SearchPatternToBuild, GeneralBuild> tuple2) {
		SearchHit hit = tuple2._2;
		String filed = tuple2._1;
		Map<String, Object> map = hit.getSourceAsMap();
		Tuple2<Map<String, String>, Explanation> mapValue =   null;
		//过滤出
		if (map.containsKey(filed)) {
			Object o = map.get(filed);
			Set<Map.Entry<String, HighlightField>> entries = hit
					                                                 .getHighlightFields()
					                                                 .entrySet();
			Set<String> setKey = entries
					                     .stream()
					                     .filter(Objects::nonNull)
					                     .map(Map.Entry::getKey)
					                     .filter(Objects::nonNull)
					                     .filter(key -> StringUtils.equals(key, filed))
					                     .collect(Collectors.toSet());
			mapValue = entries
					        .stream()
					        .filter(Objects::nonNull)
					        .filter(setKey::contains)
					        .map(Map.Entry::getValue)
					        .filter(Objects::nonNull)
					        .map(highlightField -> Lists.newArrayList(highlightField.getFragments()))
					        .flatMap(Collection::stream)
					        .filter(Objects::nonNull)
					        .map(frag -> Tuple.of(frag, o, tuple2._3, tuple2._4, hit))
					        .map(ConvertSearchBuilderUtils::formatValue)
					        .findAny()
					        .orElse(null);
			
			
		}
		
		return mapValue;
	}
	
	/**
	 * 格式的值
	 *
	 * @param tuple2
	 * 		Tuple5<Text, Object, SearchPatternToBuild, GeneralBuild, SearchHit>   HighlightField.getFragments result SearchPatternToBuild, GeneralBuild, SearchHit
	 *
	 * @return {@link Map<String, String>}
	 */
	private static  Tuple2<Map<String, String>, Explanation> formatValue(Tuple5<Text, Object, SearchPatternToBuild, GeneralBuild, SearchHit> tuple2) {
		String texts = tuple2._2.toString();
		String fragStr = tuple2._1.string();
		List<Attr> indexes = tuple2._3.getIndexes();
		String noEmStr = fragStr.replaceAll("<em>", "");
		noEmStr = noEmStr.replaceAll("</em>", "");
		if (texts != null && texts.length() > 0) {
			texts = texts.replace(noEmStr, fragStr);
		}
		Map<String, String> map = Maps.newHashMap();
		map.put(tuple2._4.getName(), texts);
		String index = tuple2._5.getIndex();
		//判断是否存在多模式索引
		if (!indexes.isEmpty()) {
			String indexPrefix = index.replaceAll("\\d+", "");
			Attr attr = indexes
					            .stream()
					            .filter(in -> StringUtils.equals(indexPrefix, in.getIndexPrefix()))
					            .findAny()
					            .orElseThrow(NullPointerException::new);
			map.put("index", indexPrefix);
			map.put("chineseAlas", attr.getChineseNameAlias());
			
			
		} else {
			map.put("index", tuple2._5.getIndex());
		
			
		}
		
		
		return Tuple.of(map,tuple2._5.getExplanation());
	}
	
	
}
