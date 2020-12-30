package com.example.typehandle.entity;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


/**
 * 用户实体
 *
 * @author shizeying
 * @date 2020/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsersEntity extends BasicEntity {
	
	
	private Map<String, Object> map = Maps.newHashMap();
	
	
	public void setMap(Map<String, Object> map) {
		this.map.putAll(map);
	}
}
