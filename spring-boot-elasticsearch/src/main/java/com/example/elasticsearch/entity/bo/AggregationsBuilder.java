package com.example.elasticsearch.entity.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 聚合构建器
 *
 * @author shizeying
 * @date 2021/01/01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AggregationsBuilder {
	private List<Object> detail;
	private Integer type;
	private String column;
	private Integer size;
	private List<String> exclude;
	private List<String> include ;
	
	
}
