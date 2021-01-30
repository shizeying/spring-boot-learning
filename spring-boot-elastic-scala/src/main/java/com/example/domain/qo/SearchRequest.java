package com.example.domain.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 搜索请求
 *
 * @author shizeying
 * @date 2021/01/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
	
	
	private List<? extends CustomQuery> queries;

}
