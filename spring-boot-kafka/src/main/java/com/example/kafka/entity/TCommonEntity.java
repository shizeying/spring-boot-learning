package com.example.kafka.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Data;

@Data
@SuppressWarnings("ALL")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TCommonEntity implements Serializable {
	
	/**
	 * 关联Id
	 */
	private String reId;
	
}
