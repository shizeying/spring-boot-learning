package com.example.typehandle.entity;

import com.example.typehandle.annotation.ToMap;
import lombok.Data;

/**
 * 基本的实体
 *
 * @author shizeying
 * @date 2020/12/30
 */
@Data
public abstract class BasicEntity extends BaseEntity{
	@ToMap(key = "list123")
	private String name;
}
