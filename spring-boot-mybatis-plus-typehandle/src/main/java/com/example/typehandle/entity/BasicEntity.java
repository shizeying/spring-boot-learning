package com.example.typehandle.entity;

import com.example.typehandle.annotation.ToMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基本的实体
 *
 * @author shizeying
 * @date 2020/12/30
 */
@Data
@NoArgsConstructor
public abstract class BasicEntity extends BaseEntity{
	@ToMap(key = "list123")
	private String name;
	
	public BasicEntity(Long id, String name) {
		super(id);
		this.name = name;
	}
}
