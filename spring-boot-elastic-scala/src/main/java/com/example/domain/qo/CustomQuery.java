package com.example.domain.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义查询
 *
 * @author shizeying
 * @date 2021/01/16
 */

public class CustomQuery {
	
	/**
	 * 中文名称
	 */
	private String name;
	/**
	 * es映射field字段
	 */
	private String column;
	/**
	 * es的field类型
	 */
	private String esType;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
	public String getEsType() {
		return esType;
	}
	
	public void setEsType(String esType) {
		this.esType = esType;
	}
	
	public CustomQuery() {
	}
	
	public CustomQuery(String name, String column, String esType) {
		this.name = name;
		this.column = column;
		this.esType = esType;
	}
}
