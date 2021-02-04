package com.example.utils

import com.example.domain.Result


case class SingleData (chineseName: String, fieldName: String, data: Any)


/**
	* @program: spring-boot-learning->HitResult
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-15 20:49
	* */
case class HitResult (override val id: String, override val index: String, dataList: java.util.List[SingleData]) extends Result


object HitResult {
	
	import com.sksamuel.elastic4s.requests.searches.{SearchHit, SearchHits}
	
	import java.util.Objects
	import scala.collection.JavaConverters.seqAsJavaListConverter
	
	/**
		* @see http://stackoverflow.com/questions/20684572/
		*/
	def unapply (m: SearchHit): Option[HitResult] = try {
		
		import scala.collection.JavaConverters.seqAsJavaListConverter
		
	
		
		val datas = m
			.sourceAsMap
			.filter (Objects.nonNull)
			.map {
				res => Tuple2 (res._1, res._2)
			}
			.filter (res => !res._2.isInstanceOf [Map[Any, Any]])
			.map {
				res =>
					import org.apache.commons.lang3.StringUtils
					Tuple2 (res._1, m.highlightFragments (res._1).map (_ + "").headOption.filter (StringUtils.isNotBlank).getOrElse (res._2))
			}
			.map (SingleData.unapply)
			.map (_.orNull)
			.filter (Objects.nonNull)
			.filter (!_.fieldName.eq ("reId"))
			.toList
			.asJava
		Some (
			HitResult (
				id = m.id,
				index = m.index.replaceAll ("\\d+", ""),
				dataList = datas
			)
		
		)
		
		
	} catch {
		case ex: Exception => None
	}
	
	def apply (m: SearchHits): java.util.List[HitResult] = m.hits
		.iterator
		.filter (Objects.nonNull)
		.map (unapply)
		.map (_.get)
		.filter (Objects.nonNull)
		.toList
		.asJava
	
}

object SingleData {
	
	def unapply (m: (String, Any)): Option[SingleData] = try {
		Some (SingleData (
			chineseName = "",
			fieldName = m._1.asInstanceOf [String],
			data = m._2.asInstanceOf [Any]
		))
	} catch {
		case ex: Exception => None
	}
	
}