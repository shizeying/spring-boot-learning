package com.example.domain


/**
	* @program: spring-boot-learning->RsData
	* @description: ${description}
	* @author: shizeying
	* @create: 2021-01-14 15:10
	* */
trait data

abstract class BasicRsData (id: String)

case class SubsetData (chineseName: String, column: String, value: Any)

case class RsData (id: String, dataList: Option[java.util.List[SubsetData]]) extends BasicRsData (id)


object RsData {
	

}
