package com.example.config;

import com.google.common.collect.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Configuration
@Component
@PropertySource(value = {"classpath:/dev.yml"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "kg")
public class DepartmentProperties {
	/**
	 * 部门列表配置
	 */
	private Map<String, List<String>> departmentMap = Maps.newHashMap();
	/**
	 * 0：key为部门
	 * 1：key账号
	 */
	private int key = 0;
	
	public DepartmentProperties() {
	}
	
	public Map<String, List<String>> getDepartmentMap() {
		return departmentMap;
	}
	
	public void setDepartmentMap(final Map<String, List<String>> departmentMap) {
		this.departmentMap = departmentMap;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(final int key) {
		this.key = key;
	}
	
	public DepartmentProperties(final Map<String, List<String>> departmentMap, final int key) {
		this.departmentMap = departmentMap;
		this.key = key;
	}
	
	@Override
	public String toString() {
		return "DepartmentProperties{" +
				       "departmentMap=" + departmentMap +
				       ", key=" + key +
				       '}';
	}
}
