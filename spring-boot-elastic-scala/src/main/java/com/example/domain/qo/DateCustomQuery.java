package com.example.domain.qo;

import com.example.domain.bo.CustomRangeEntity;
import com.example.enumer.RangeEnum;
import lombok.*;

/**
 * 自定义日期查询
 *
 * @author shizeying
 * @date 2021/01/16
 */

public class DateCustomQuery extends CustomQuery {
	private RangeEnum fromK;
	//private String fromK;
	private String fromV;
	private RangeEnum toK;
	//private String toK;
	private String toV;
	
	public RangeEnum getFromK() {
		return fromK;
	}
	
	public void setFromK(RangeEnum fromK) {
		this.fromK = fromK;
	}
	
	public String getFromV() {
		return fromV;
	}
	
	public void setFromV(String fromV) {
		this.fromV = fromV;
	}
	
	public RangeEnum getToK() {
		return toK;
	}
	
	public void setToK(RangeEnum toK) {
		this.toK = toK;
	}
	
	public String getToV() {
		return toV;
	}
	
	public void setToV(String toV) {
		this.toV = toV;
	}
	
	public DateCustomQuery(String name, String column, String esType, RangeEnum fromK, String fromV, RangeEnum toK, String toV) {
		super(name, column, esType);
		this.fromK = fromK;
		this.fromV = fromV;
		this.toK = toK;
		this.toV = toV;
	}
}
