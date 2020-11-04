package com.example.kafka.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("ALL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class RelationEntity extends BasicEntity {
	
	/** 开始实体名 */
	
	private String entityName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Setter
	private Date timeFrom;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Setter
	private Date timeTo;
	
	/** 实体关系 */
	private String relName;
	/** 结束实体名 */
	private String valueName;
	/** 开始实体类型 */
	private String entityType;
	
	/** 结束实体类型 */
	private String valueType;
	
	/** 边属性 */
	@Setter
	private Map<String, Object> edgeMap;
	
	public void setEntityName(String entityName) {
		this.entityName = entityName != null ? entityName.trim() : null;
	}
	
	public void setRelName(String relName) {
		this.relName = relName != null ? relName.trim() : null;
	}
	
	public void setValueName(String valueName) {
		this.valueName = valueName != null ? valueName.trim() : null;
	}
	
	public void setEntityType(String entityType) {
		this.entityType = entityType != null ? entityType.trim() : null;
	}
	
	public void setValueType(String valueType) {
		this.valueType = valueType != null ? valueType.trim() : null;
	}
}
