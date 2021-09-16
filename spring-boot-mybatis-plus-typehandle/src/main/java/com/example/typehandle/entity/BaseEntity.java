package com.example.typehandle.entity;

import com.example.typehandle.annotation.ToMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shizeying
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {
	@ToMap(key = "listid")
	private Object id;
}
