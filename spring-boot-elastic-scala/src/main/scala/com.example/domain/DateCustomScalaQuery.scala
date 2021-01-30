package com.example.domain

object RangeScalaEnum extends Enumeration {
	type rangeEnum = Value
	val gte, gt, lt, lte = Value
}


case class RangeQuery (rangeType: RangeScalaEnum.rangeEnum, v: String)

/**
	* @program: spring-boot-learning->DateCustomQuery
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-17 13:10
	* */
case class DateCustomScalaQuery (
	
	                                     /**
		                                     * 中文名称
		                                     */
	                                     override val name: String,
	                                     /**
		                                     * es映射field字段
		                                     */
	                                     override val column: String,
	                                     /**
		                                     * es的field类型
		                                     */
	                                     override val esType: String,
	                                     form: RangeQuery,
	                                     to: RangeQuery,
                                     ) extends CustomScalaQuery


object DateCustomScalaQuery {
	
	import com.example.domain.qo.DateCustomQuery
	
	def unapply (dateQuery: DateCustomQuery) = try {
		import com.example.enumer.RangeEnum
		val formk = dateQuery.getFromK
		val tok = dateQuery.getToK
		val formRange = formk match {
			case RangeEnum.GT => RangeScalaEnum.gt
			case RangeEnum.GTE => RangeScalaEnum.gte
		}
		val formV = dateQuery.getFromV
		val toRange = tok match {
			case RangeEnum.LT => RangeScalaEnum.lt
			case RangeEnum.LTE => RangeScalaEnum.lte
		}
		val toV = dateQuery.getFromV
		val form = RangeQuery (formRange, formV)
		val to = RangeQuery (toRange, toV)
		/**
			* 中文名称
			*/
		val name: String = dateQuery.getName
		/**
			* es映射field字段
			*/
		val column: String = dateQuery.getColumn
		/**
			* es的field类型
			*/
		val esType: String = dateQuery.getEsType
		
		Some (DateCustomScalaQuery (
			name = name,
			column = column,
			esType = esType,
			form = form,
			to = to
		)
		)
	} catch {
		case ex: Exception => None
	}
	
	
}
