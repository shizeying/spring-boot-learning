package com.example.domain.bo;



/**
 * 自定义实体
 *
 * @author shizeying
 * @date 2021/01/16
 */

public class CustomRangeEntity {
	/**
	 * 运算符号
	 */
	
	private String k;
	/**
	 * 返回值
	 */
	private String v;
	
	public String getK() {
		return k;
	}
	
	public void setK(String k) {
		this.k = k;
	}
	
	public String getV() {
		return v;
	}
	
	public void setV(String v) {
		this.v = v;
	}
	
	public CustomRangeEntity(String k, String v) {
		this.k = k;
		this.v = v;
	}
}
