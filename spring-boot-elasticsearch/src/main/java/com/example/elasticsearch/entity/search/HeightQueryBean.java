package com.example.elasticsearch.entity.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 高度查询bean
 *
 * @author shizeying
 * @date 2021/01/02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HeightQueryBean {
	/**
	 * 字段
	 */
	private String column;
	/**
	 * 0：精确 1：模糊
	 */
	private String type;
	/**
	 * 0：不分词 1：分词
	 */
	private String iscore;
	/**
	 * 值,搜索词
	 */
	private String value;
	/**
	 * 0：并且  1：或者 2：不含
	 */
	private String midd;
	
}
