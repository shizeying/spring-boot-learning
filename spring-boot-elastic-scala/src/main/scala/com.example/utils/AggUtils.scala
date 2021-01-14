package com.example.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.sksamuel.elastic4s.requests.searches.DateHistogramInterval
import org.apache.commons.lang3.StringUtils

import java.util.Objects
import scala.collection.JavaConverters.collectionAsScalaIterableConverter

@JsonInclude (JsonInclude.Include.NON_NULL)
case class AttrAggK (name: String, columnItem: String, esType: String, interval: String, format: String, include: java.util.List[String],
                     exclude: java
                     .util
                     .List[String],
                     `type`: String, size: Integer, parentId: Long, detail: java.util.List[String]) extends Serializable

@JsonInclude (JsonInclude.Include.NON_NULL)
case class AttrAggV (aggSum: Long, value: Any, key: Any)

case class AttrAgg (k: AttrAggK, v: java.util.List[AttrAggV], d: String)

object AggUtils {
	def main (args: Array[String]): Unit ={
		import com.sksamuel.elastic4s.JacksonSupport
		printf(		JacksonSupport.mapper.writeValueAsString(AttrAgg)
		)
	}
	
	import java.time.LocalDate
	
	/**
		* 构造Terms聚合
		*
		* @param k k
		*/
	
	def builderTermsAgg (attrAggKs: java.util.List[AttrAggK]) = {
		
		
		attrAggKs
			.asScala
			.filter (Objects.nonNull)
			.filter (res => StringUtils.isNoneBlank (res.columnItem))
			.map {
				attr => {
					import com.sksamuel.elastic4s.requests.searches.aggs.{TermsAggregation, TermsOrder}
					val key = "key" + attr.name
					val column = if (attr.esType.equalsIgnoreCase ("date")) attr.columnItem.replaceAll ("\\.keyword", "")
					else attr.columnItem.replaceAll ("\\.keyword", "") + ".keyword"
					val aggSize = if (Objects.nonNull (attr.size)) attr.size.intValue () else 30
					val termsAggregation = TermsAggregation.apply (key).field (column).size (aggSize)
					if (!attr.include.isEmpty) termsAggregation.includeExactValues (attr.include.asScala.toSeq)
					if (!attr.exclude.isEmpty) termsAggregation.includeExactValues (attr.exclude.asScala.toSeq)
					if (attr.esType.equalsIgnoreCase ("date")) termsAggregation.order (TermsOrder.apply ("_key", asc = true))
					termsAggregation
					
					
				}
			}
	}
	
	/**
		* 构造日期范围聚合
		*
		* @param attrAggKs attr聚合ks
		* @param form      之前
		* @param to        之后
		*
		* @return {  @link Iterable < DateRangeAggregation >  }
		*/
	def builderDateRangeAgg (attrAggKs: java.util.List[AttrAggK], form: LocalDate, to: LocalDate) =
		attrAggKs
			.asScala
			.filter (Objects.nonNull)
			.filter (res => StringUtils.isNoneBlank (res.columnItem))
			.filter (res => StringUtils.isNoneBlank (res.interval))
			.filter (res => StringUtils.equalsAnyIgnoreCase (res.esType, "date"))
			.map {
				attr => {
					import com.sksamuel.elastic4s.requests.searches.aggs.DateRangeAggregation
					import com.sksamuel.elastic4s.ElasticDate
					val key = "key" + attr.name
					val column = attr.columnItem.replaceAll ("\\.keyword", "")
					val format = if (StringUtils.isNotBlank (attr.format)) attr.format else "yyyy-mm-dd hh:mm:ss"
					val formDate = ElasticDate.apply (form)
					val toDate = ElasticDate.apply (to)
					DateRangeAggregation.apply (key).field (column).format (format).range (formDate, toDate)
				}
			}
	
	/**
		* 构造日期柱状图
		*
		*/
	def builderDateHistogramAgg (attrAggKs: java.util.List[AttrAggK]) =
		attrAggKs
			.asScala
			.filter (Objects.nonNull)
			.filter (res => StringUtils.isNoneBlank (res.columnItem))
			.filter (res => StringUtils.isNoneBlank (res.interval))
			.filter (res => StringUtils.equalsAnyIgnoreCase (res.esType, "date"))
			.map {
				attr => {
					import com.sksamuel.elastic4s.requests.searches.aggs.DateHistogramAggregation
					val key = "key" + attr.name
					val column = attr.columnItem.replaceAll ("\\.keyword", "")
					val interval = getDateHistogramInterval (attr.interval)
					val format = if (StringUtils.isNotBlank (attr.format)) attr.format else "yyyy-mm-dd hh:mm:ss"
					DateHistogramAggregation.apply (key).field (column).format (format).fixedInterval (interval).minDocCount (30)
				}
			}
	
	private def getDateHistogramInterval (interval: String): DateHistogramInterval = interval match {
		case "seconds" =>
			DateHistogramInterval.Second
		case "minute" =>
			DateHistogramInterval.Minute
		case "hour" =>
			DateHistogramInterval.Hour
		case "day" =>
			DateHistogramInterval.Day
		case "week" =>
			DateHistogramInterval.Week
		case "month" =>
			DateHistogramInterval.Month
		case "quarter" =>
			DateHistogramInterval.Quarter
		case "year" =>
			DateHistogramInterval.Year
		case _ =>
			throw new NullPointerException ("interval 传入有问题")
	}
}
