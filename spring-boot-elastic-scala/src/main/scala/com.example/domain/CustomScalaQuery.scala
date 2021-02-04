package com.example.domain

trait CustomScalaQuery {
	/**
		* 中文名称
		*/
	val name: String
	/**
		* es映射field字段
		*/
	val column: String
	/**
		* es的field类型
		*/
	val esType: String
}
