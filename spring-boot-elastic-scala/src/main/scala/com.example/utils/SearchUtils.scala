package com.example.utils

import com.sksamuel.elastic4s.requests.searches.queries.matches.MatchPhrasePrefix
import com.sksamuel.elastic4s.requests.searches.queries.term.TermQuery
import com.sksamuel.elastic4s.ElasticApi.{constantScoreQuery, idsQuery}
import com.sksamuel.elastic4s.requests.searches.queries.{FuzzyQuery, Query}

import java.util.Objects
import scala.collection.JavaConverters.collectionAsScalaIterableConverter

/**
	* 高亮实体
	* column：field
	* fragmentSize：参数用于控制返回的高亮字段的最大字符数（默认值为 100 ），如果高亮结果的字段长度大于该设置的值，则大于的部分不返回
	*
	* @author shizeying
	* @date 2021 /01/14
	*/
case class HighlightEntity (column: String, fragmentSize: Integer)

/**
	* Term
	* column：field
	* kw :关键字
	* boots：权重
	* include：包含的字段
	* exclude：不包含的字段
	*
	* @author shizeying
	* @date 2021 /01/14
	*/
trait SearchEntity {
	val column: String
	val words: java.util.List[String]
	val boots: Integer
}

case class TermEntity (override val column: String, override val words: java.util.List[String], override val boots: Integer) extends
	SearchEntity 

case class MatchPhrasePrefixEntity (override val column: String, override val words: java.util.List[String], override val boots: Integer) extends
	SearchEntity

case class WildcardEntity (override val column: String, override val words: java.util.List[String], override val boots: Integer) extends
	SearchEntity

//case class TermEntity (override val column: String,override val kw: String,override val boots: Integer,
//                       include: java.util.List[String], exclude: java
//.util.List[String])  extends
//	SearchEntity(column,kw ,boots)
/**
	* @program: spring-boot-learning->SearchUtils
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-14 18:07
	* */
object SearchUtils {
	
	import com.sksamuel.elastic4s.requests.searches.queries.ConstantScore
	
	def builderHighlight (highlights: java.util.List[HighlightEntity]) = {
		import com.sksamuel.elastic4s.ElasticApi.highlight
		
		import scala.collection.JavaConverters.asScalaBufferConverter
		highlights.asScala
			.map (
				rest => {
					val hig = highlight (rest.column)
					if (Objects.nonNull (rest.fragmentSize)) {
						hig.fragmentOffset (rest.fragmentSize)
					}
					hig
				}
			)
		
	}
	
	
	/** builder词查询
		* 构建 term查询，如果存在boots则自动转换为  constantScoreQuery
		*
		* @param termEntities 术语实体
		*
		* @return {     @link Iterable < Query >     }
		*
		* @param termEntities 术语实体
		* @param isSegment    是否进行分词
		*
		* @return {   @link Iterable < Query >   }
		*/
	def builderTermQueries (termEntities: java.util.List[TermEntity]) = {
		
		termEntities
			.asScala
			.filter (Objects.nonNull)
			.map {
				termEntity => {
					import com.sksamuel.elastic4s.ElasticApi.termQuery
					val term = termQuery (termEntity.column, termEntity.words)
					termScore (termEntity, term)
				}
			}
	}
	
	private def termScore (termEntity: TermEntity, term: TermQuery): Query = {
		import com.sksamuel.elastic4s.ElasticApi.constantScoreQuery
		if (Objects.nonNull (termEntity.boots)) {
			constantScoreQuery (term).boost (termEntity.boots.doubleValue ())
		} else {
			term
		}
	}
	
	/**
		* 构建器模糊查询
		*
		* @param fuzzEntities 会自动分词
		*
		* @return {  @link Iterable < Iterable < Query > >  }
		*/
	def builderFuzzyQuery (fuzzEntities: java.util.List[TermEntity]): Iterable[Query] = {
		fuzzEntities
			.asScala
			.filter (Objects.nonNull)
			.map {
				fuzzEntity => {
					import com.sksamuel.elastic4s.ElasticApi.fuzzyQuery
					fuzzEntity.words
						.asScala
						.filter (Objects.nonNull)
						.map { word =>
							convertFuzzyScore (fuzzEntity, fuzzyQuery (fuzzEntity.column, word))
						}.iterator
				}
			}
			.flatMap (_.toIterator)
		
	}
	
	private def convertFuzzyScore (fuzzEntity: TermEntity, fuzzy: FuzzyQuery): Query = {
		if (Objects.nonNull (fuzzEntity.boots)) {
			constantScoreQuery (fuzzy).boost (fuzzEntity.boots.doubleValue ())
		} else {
			fuzzy
		}
	}
	
	/**
		* 构建器匹配查询词的前缀
		*
		*/
	def builderMatchPhrasePrefixQuery (prefixEntities: java.util.List[MatchPhrasePrefixEntity]): Iterable[MatchPhrasePrefix] = {
		
		prefixEntities
			.asScala
			.filter (Objects.nonNull)
			.map {
				phrasePrefixEntity => {
					import com.sksamuel.elastic4s.ElasticApi.matchPhrasePrefixQuery
					phrasePrefixEntity.words
						.asScala
						.filter (Objects.nonNull)
						.map {
							word =>
								val phrase = matchPhrasePrefixQuery (phrasePrefixEntity.column, word).maxExpansions (3)
								if (Objects.nonNull (phrasePrefixEntity.boots)) {
									phrase.boost (phrasePrefixEntity.boots.doubleValue ())
								}
								phrase
						}
						.iterator
				}
			}.flatMap (_.toIterable)
	}
	
	/**
		* 构建器匹配短语查询
		* 前缀查询 建议2-5个字
		*
		* @param prefixEntities 前缀的实体
		* @param isSegment      是段
		*
		* @return {  @link BoolQuery  }
		*/
	def builderMatchPhraseQuery (prefixEntities: java.util.List[MatchPhrasePrefixEntity], isSegment: Boolean) = {
		import com.sksamuel.elastic4s.ElasticApi.boolQuery
		val boolMatchPhraseQuery = boolQuery.minimumShouldMatch (1)
		prefixEntities
			.asScala
			.filter (Objects.nonNull)
			.map {
				phrasePrefixEntity => {
					import com.sksamuel.elastic4s.ElasticApi.matchPhraseQuery
					phrasePrefixEntity.words.asScala
						.filter (Objects.nonNull)
						.map {
							word =>
								val phrase = matchPhraseQuery (phrasePrefixEntity.column, word)
								val score = constantScoreQuery (phrase)
								if (Objects.nonNull (phrasePrefixEntity.boots)) {
									score.boost (phrasePrefixEntity.boots.doubleValue ())
								}
								score
						}.iterator
				}
			}.flatMap (_.toIterable).map (boolMatchPhraseQuery.should (_))
		boolMatchPhraseQuery
	}
	
	/**
		* builder通配符查询
		*
		* @param wildcardEntities 通配符的实体
		* @param isSegment        是分词
		*/
	def builderWildcardQuery (wildcardEntities: java.util.List[WildcardEntity]) = {
		wildcardEntities.asScala
			.filter (Objects.nonNull)
			.map {
				wildcardEntity => {
					import com.sksamuel.elastic4s.ElasticApi.boolQuery
					val boolQueries = boolQuery ().minimumShouldMatch (1)
					wildcardEntity.words
						.asScala
						.filter (Objects.nonNull)
						.map {
							word =>
								import com.sksamuel.elastic4s.ElasticApi.wildcardQuery
								wildcardQuery (wildcardEntity.column, "*" + word + "*")
						}.map { wildcard => boolQueries.should (wildcard) }
					
					
				}
			}
			.filter (Objects.nonNull)
			.flatMap (_.iterator)
	}
	
	
	/**
		* 构建ids过滤类型
		*
		* @param ids id
		*
		* @return {  @link IdQuery  }
		*/
	def buildFilterIds (ids: java.util.List[String]) = idsQuery (ids)
	
	
	def segmentKw (kw: String) = {
		import com.hankcs.hanlp.HanLP
		
		
		val segment = HanLP.newSegment ("viterbi")
		segment.seg2sentence (kw)
			.asScala
			.filter (Objects.nonNull)
			.map {
				_.asScala
					.filter (Objects.nonNull)
					.map (_.word)
				
			}
			.flatMap (_.iterator)
			.filter (_.length >= 2)
	}
	
	/** 构建器精确条款查询
		*
		* @param termEntities 术语实体
		*
		* @return {      @link BoolQuery      }
		*
		* @param termEntities term实体
		*
		* @return {    @link BoolQuery    }
		*/
	def builderFineTermsQuery (termEntities: java.util.List[TermEntity]) = {
		import com.sksamuel.elastic4s.ElasticApi.boolQuery
		val funcQuery = boolQuery.minimumShouldMatch (0)
		termEntities.asScala
			.filter (Objects.nonNull)
			.map {
				term => {
					import com.sksamuel.elastic4s.ElasticApi.termsQuery
					val score = constantScoreQuery (termsQuery (term.column, term.words))
					fineTermQuery (term, score)
					
				}
			}.filter (Objects.nonNull)
			.foreach (funcQuery.should (_))
		funcQuery
	}
	
	private def fineTermQuery (term: TermEntity, score: ConstantScore): ConstantScore = {
		if (Objects.nonNull (term.boots)) {
			score.boost (term.boots.doubleValue ())
		} else {
			score.boost (0.01f)
		}
		score
	}
	
	/**
		* 构建器正则表达式查询
		*
		* @param wildcardEntities 通配符的实体
		*
		* @return {  @link BoolQuery  }
		*/
	def builderRegexpQuery (wildcardEntities: java.util.List[WildcardEntity]) = {
		import com.sksamuel.elastic4s.ElasticApi.boolQuery
		val regexpBool = boolQuery ()
		wildcardEntities
			.asScala
			.filter (Objects.nonNull)
			.map {
				wildcardEntity =>
					import com.sksamuel.elastic4s.requests.searches.queries.{RegexpFlag, RegexQuery}
					import org.apache.commons.lang3.StringUtils
					wildcardEntity.words
						.asScala
						.filter (StringUtils.isNoneBlank (_))
						.map (word => RegexQuery (wildcardEntity.column, word).flags (RegexpFlag.All).maxDeterminedStates (10000))
						.map (ConstantScore (_))
						.map { score =>
							if (Objects.nonNull (wildcardEntity.boots)) {
								score.boost (wildcardEntity.boots.doubleValue ())
							}
							score
						}
				
			}.filter (Objects.nonNull)
			.flatMap (_.iterator)
			.foreach (regexpBool.should (_))
		
		regexpBool
	}
}
