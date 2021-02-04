package com.example.domain

/**
	* @program: spring-boot-learning->HigCustomQuery
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-17 13:11
	* */
case class HigCustomScalaQuery (override val name: String,
                                override val column: String,
                                override val esType: String,
                                /**
	                                * 搜索词
	                                */
                                kw: Iterable[String],
                                /**
	                                * 细粒度
	                                */
                                isFine: Boolean,
                                /**
	                                * 细粒度权重
	                                */
                                fineBoots: Double,
                                /**
	                                * 粗粒度
	                                */
                                isCoarse: Boolean,
                                /**
	                                * 粗粒度权重
	                                */
                                coarseBoots: Double

                               ) extends CustomScalaQuery

object HigCustomScalaQuery {
	
	import com.example.domain.qo.HigCustomQuery
	
	def unapply (higQuery: HigCustomQuery) = try {
		import com.example.utils.SearchUtils
		
		val kw = if (higQuery.getParticiple)
			(SearchUtils.segmentKwScala (higQuery.getKw) ++ SearchUtils.segmentKwOverloadScala (higQuery.getKw) ++ Iterator (higQuery.getKw))
		else Iterable (higQuery.getKw)
		val name = higQuery.getName
		val column = higQuery.getColumn
		val esType = higQuery.getEsType
		val isFine = higQuery.getFine
		val fineBoots = higQuery.getCoarseBoots
		val isCoarse = higQuery.getCoarse
		val coarseBoots = higQuery.getCoarseBoots

		Some (HigCustomScalaQuery (
			name = name,
			column = column,
			esType = esType,
			kw = kw,
			isFine = isFine,
			fineBoots = fineBoots,
			isCoarse = isCoarse,
			coarseBoots = coarseBoots
	
		))
		
	} catch {
		case ex: Exception => None
	}
	
	
}