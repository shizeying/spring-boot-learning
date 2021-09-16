package com.example.typehandle.entity;

import com.google.common.collect.Maps;
import lombok.*;

import java.util.Map;


/**
 * 用户实体
 *
 * @author shizeying
 * @date 2020/12/29
 */
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class TestUsersEntity extends BasicEntity {
	
	
	private Map<String, Object> map = Maps.newHashMap();
	
	@Builder
	public TestUsersEntity(Object id, String name, Map<String, Object> map) {
		super(id, name);
		this.map = map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map.putAll(map);
	}
}
