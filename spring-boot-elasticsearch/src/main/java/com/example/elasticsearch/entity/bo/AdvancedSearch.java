package com.example.elasticsearch.entity.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 高级搜索
 *
 * @author shizeying
 * @date 2021/01/01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvancedSearch {
	private Object detail = new Object();
	private Integer type = 0;
	
	
}
