package com.example.domain.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 高的自定义查询
 *
 * @author shizeying
 * @date 2021/01/16
 */

public class HigCustomQuery extends CustomQuery {
	/**
	 * 搜索词
	 */
	private String kw;
	/**
	 * 细粒度
	 */
	private Boolean isFine = false;
	/**
	 * 细粒度权重
	 */
	private Double fineBoots;
	/**
	 * 粗粒度
	 */
	private Boolean isCoarse = true;
	/**
	 * 粗粒度权重
	 */
	private Double coarseBoots;
	/**
	 * 是否分词
	 */
	private Boolean isParticiple = true;
	
	public String getKw() {
		return kw;
	}
	
	public void setKw(String kw) {
		this.kw = kw;
	}
	
	public Boolean getFine() {
		return isFine;
	}
	
	public void setFine(Boolean fine) {
		isFine = fine;
	}
	
	public Double getFineBoots() {
		return fineBoots;
	}
	
	public void setFineBoots(Double fineBoots) {
		this.fineBoots = fineBoots;
	}
	
	public Boolean getCoarse() {
		return isCoarse;
	}
	
	public void setCoarse(Boolean coarse) {
		isCoarse = coarse;
	}
	
	public Double getCoarseBoots() {
		return coarseBoots;
	}
	
	public void setCoarseBoots(Double coarseBoots) {
		this.coarseBoots = coarseBoots;
	}
	
	public Boolean getParticiple() {
		return isParticiple;
	}
	
	public void setParticiple(Boolean participle) {
		isParticiple = participle;
	}
	
	public HigCustomQuery() {
	}
	
	public HigCustomQuery(String name, String column, String esType, String kw, Boolean isFine, Double fineBoots, Boolean isCoarse,
	                      Double coarseBoots, Boolean isParticiple) {
		super(name, column, esType);
		this.kw = kw;
		this.isFine = isFine;
		this.fineBoots = fineBoots;
		this.isCoarse = isCoarse;
		this.coarseBoots = coarseBoots;
		this.isParticiple = isParticiple;
	}
}
