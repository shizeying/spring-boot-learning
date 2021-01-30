package com.example.domain

/**
	* @program: spring-boot-learning->RangeEntity
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-17 18:07
	* */
case class RangeEntity (
	                       override val column: String,
	                       override val boots: java.lang.Double,
	                       from: RangeQuery,
	                       to: RangeQuery
                       ) extends
	SearchEntity

object RangeEntity {
	
	def unapply (customScalaQuery: DateCustomScalaQuery) = Some (
		RangeEntity (
			column = customScalaQuery.column,
			boots = null,
			from = customScalaQuery.form,
			to = customScalaQuery.to
		)
	
	)
	
}