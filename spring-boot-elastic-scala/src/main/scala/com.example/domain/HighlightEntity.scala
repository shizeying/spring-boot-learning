package com.example.domain

/**
	* 高亮实体
	* column：field
	* fragmentSize：参数用于控制返回的高亮字段的最大字符数（默认值为 100 ），如果高亮结果的字段长度大于该设置的值，则大于的部分不返回
	*
	* @author shizeying
	* @date 2021 /01/14
	*/
case class HighlightEntity (column: String, fragmentSize: Integer)

object HighlightEntity {
	def unapplySingle (columnItem: String) =
		HighlightEntity (
			column = columnItem,
			fragmentSize = null
		)
	
	def unapply (column: String, fragmentSize: Integer) =
		HighlightEntity (
			column = column,
			fragmentSize = fragmentSize
		)
	
	
}
