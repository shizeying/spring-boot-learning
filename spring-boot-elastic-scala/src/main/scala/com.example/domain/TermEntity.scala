package com.example.domain

/**
	* @program: spring-boot-learning->TermEntity
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-17 17:49
	* */

case class TermEntity (override val column: String,
                       words: java.util.List[String],
                       override val boots: java.lang.Double) extends
	SearchEntity


object TermEntity {
	
	import scala.collection.JavaConverters.seqAsJavaListConverter
	
	def applyCoarse (hig: HigCustomScalaQuery) = TermEntity (
		column = hig.column,
		words = hig.kw.toList.asJava,
		boots = hig.coarseBoots
	
	)
	
	def applyFine (hig: HigCustomScalaQuery) = TermEntity (
		column = hig.column,
		words = hig.kw.toList.asJava,
		boots = hig.fineBoots
	)
}
