package com.example.elasticsearch.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 聚合agg构建单一变量
 *
 * @author shizeying
 * @date 2021/01/02
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AttrAgg {
	private AttrAggK k;
	
	
	private List<AttrAggV> v;
	private String d;
	
	
}
