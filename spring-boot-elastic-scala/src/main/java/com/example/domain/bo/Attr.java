package com.example.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * attr
 *
 * @author shizeying
 * @date 2021/01/01
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attr  implements Serializable {
	/** 指数的前缀 */
	private String indexPrefix;
	
	/** 中文名称的别名 */
	private String chineseNameAlias;
	
	@Override
	public String toString() {
		return indexPrefix + "|" + chineseNameAlias;
	}
}
