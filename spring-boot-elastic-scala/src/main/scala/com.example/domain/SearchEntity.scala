package com.example.domain


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
	val boots: java.lang.Double
}