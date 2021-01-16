package com.example.utils

import com.fasterxml.jackson.annotation.JsonInclude

import java.util.Objects

/**
	* @program: spring-boot-learning->AggResult
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-15 11:32
	* */
@JsonInclude (JsonInclude.Include.NON_NULL)
case class AttrAggK (name: String, columnItem: String, esType: String, interval: String, format: String, include: java.util.List[String],
                     exclude: java
                     .util
                     .List[String],
                     `type`: String, size: Integer, sum: String, parentId: java.lang.Long, detail: java.util.List[String]) extends Serializable

@JsonInclude (JsonInclude.Include.NON_NULL)
case class AttrAggV (key: Any, value: Any, aggSum: Long)

@JsonInclude (JsonInclude.Include.NON_NULL)
case class AttrAgg (k: AttrAggK, v: java.util.List[AttrAggV], d: String)

object AttrAgg {
	
	import com.sksamuel.elastic4s.requests.searches.aggs.responses.Aggregations
	
	/**
		* Generates a user given a Map (from ES)
		*
		* @throws scala.MatchError if not applied properly
		*/
	def apply (m: Aggregations, a: AttrAgg) = {
		import com.google.common.collect.Lists
		
		import scala.collection.JavaConverters.{collectionAsScalaIterableConverter, seqAsJavaListConverter}
		
		
		val vs = a.v
		val name = "key" + a.k.name
		
		val valueHistogram = Option.apply (dateHistogram (rs = m, key = name))
			.filter (Objects.nonNull)
			.flatten
			.filter (Objects.nonNull)
			.map {
				import scala.collection.JavaConverters.seqAsJavaListConverter
				_.asJava
			}
			.getOrElse (Lists.newArrayList ())
		val valueRange = Option.apply (dateRange (rs = m, key = name))
			.filter (Objects.nonNull)
			.flatten
			.filter (Objects.nonNull)
			.map {
				import scala.collection.JavaConverters.seqAsJavaListConverter
				_.asJava
			}
			.getOrElse (Lists.newArrayList ())
		
		val valueTerms = Option.apply (terms (rs = m, key = name))
			.filter (Objects.nonNull)
			.flatten
			.filter (Objects.nonNull)
			.map {
				import scala.collection.JavaConverters.seqAsJavaListConverter
				_.asJava
			}
			.getOrElse (Lists.newArrayList ())
		val valueTileHashGrid = Option.apply (geoTileGrid (rs = m, key = name))
			.filter (Objects.nonNull)
			.flatten
			.filter (Objects.nonNull)
			.map {
				import scala.collection.JavaConverters.seqAsJavaListConverter
				_.asJava
			}
			.getOrElse (Lists.newArrayList ())
		val valueGeoHashGrid = Option.apply (geoHashGrid (rs = m, key = name))
			.filter (Objects.nonNull)
			.flatten
			.filter (Objects.nonNull)
			.map {
				import scala.collection.JavaConverters.seqAsJavaListConverter
				_.asJava
			}
			.getOrElse (Lists.newArrayList ())
		vs.addAll (valueTerms)
		vs.addAll (valueHistogram)
		vs.addAll (valueTerms)
		vs.addAll (valueGeoHashGrid)
		vs.addAll (valueTileHashGrid)
		val k = a.k
		val d = a.d
		new AttrAgg (k, duplicate (vs.asScala.toList).asJava, d)
	}
	
	def duplicate (list: List[AttrAggV]) = list.foldLeft (List.empty [AttrAggV]) {
		(result, cur) =>
			if (result.count (res => res.key.equals (cur.key)) > 1) result else result :+ cur
	}
	
	private def topHits (rs: Aggregations, key: String) = try {
		import com.sksamuel.elastic4s.requests.searches.aggs.responses.metrics.TopHits.TopHitsAggSerde
		rs.result (key)
	} catch {
		case _: Throwable => null
	}
	
	private def geoHashGrid (rs: Aggregations, key: String) = try {
		import com.sksamuel.elastic4s.requests.searches.aggs.responses.bucket.GeoHashGrid.GeoHashGridAggSerde
		Some (rs.result (key)
			.buckets
			.filter (Objects.nonNull)
			.map (res => Tuple2 (res.key, res.docCount))
			.map (tuple2 => AttrAggV (tuple2._1, tuple2._1, tuple2._2))
			
			.toList
		
		)
	} catch {
		case _: Throwable => null
	}
	
	private def geoTileGrid (rs: Aggregations, key: String) = try {
		import com.sksamuel.elastic4s.requests.searches.aggs.responses.bucket.GeoTileGrid.GeoTileGridAggSerde
		Some (rs.result (key)
			.buckets
			.filter (Objects.nonNull)
			.map (res => Tuple2 (res.key, res.docCount))
			.map (tuple2 => AttrAggV (tuple2._1, tuple2._1, tuple2._2))
			
			.toList
		
		)
	} catch {
		case _: Throwable => null
	}
	
	private def dateRange (rs: Aggregations, key: String) = try {
		import com.sksamuel.elastic4s.requests.searches.aggs.responses.bucket.DateRange.DateRangeAggSerde
		Some (rs.result (key)
			.buckets
			.filter (Objects.nonNull)
			.filter (res => Objects.nonNull (res.key.get))
			.filter (res => Objects.nonNull (res.docCount))
			.map (res => Tuple2 (res.key.get, res.docCount))
			.map (tuple2 => AttrAggV (tuple2._1, tuple2._1, tuple2._2))
			
			.toList
		)
	} catch {
		case _: Throwable => null
	}
	
	private def dateHistogram (rs: Aggregations, key: String) = try {
		import com.sksamuel.elastic4s.requests.searches.aggs.responses.bucket.DateHistogram.DateHistogramAggReader
		Some (rs.result (key).buckets
			.filter (Objects.nonNull)
			.map (res => Tuple2 (res.date, res.docCount))
			.map (tuple2 => AttrAggV (tuple2._1, tuple2._1, tuple2._2))
			.toList)
	} catch {
		case _: Throwable => null
	}
	
	private def terms (rs: Aggregations, key: String) = {
		try {
			import com.sksamuel.elastic4s.requests.searches.aggs.responses.bucket.Terms.TermsAggReader
			Some (rs.result (key)
				.buckets
				.filter (Objects.nonNull)
				.map (res => Tuple2 (res.key, res.docCount))
				.map (tuple2 => AttrAggV (tuple2._1, tuple2._1, tuple2._2))
				.toList)
			
		} catch {
			case _: Throwable => null
		}
	}
	
	/**
		* Generates a user given an Elasticsearch SearchHit
		* Note that it might be desirable to minimize dependencies and only allow to
		* construct this from a Map and not from a SearchHit
		*
		* @throws scala.MatchError if not applied properly
		*/
	def apply (aggs: Aggregations, as: java.util.List[AttrAgg]): java.util.List[AttrAgg] = {
		import scala.collection.JavaConverters.{asScalaBufferConverter, bufferAsJavaListConverter}
		as
			.asScala
			.filter (Objects.nonNull)
			.map {
				res: AttrAgg => AttrAgg (aggs, res)
			}.filter (Objects.nonNull)
			.asJava
	}
	
	
}




