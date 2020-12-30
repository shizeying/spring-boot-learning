package com.example.typehandle.entity;

import com.example.typehandle.annotation.ToMap;
import lombok.Data;

@Data
public abstract class BaseEntity {
	@ToMap(key = "listid")
	private Long id;
}
