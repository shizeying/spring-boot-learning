package com.example.transform.service;

import com.example.transform.properties.TransformProperties;

public class TransformService {
	private final TransformProperties transformProperties;
	
	public TransformService(TransformProperties transformProperties) {
		this.transformProperties = transformProperties;
	}
	
	
	public String hello() {
		return transformProperties.getFontPath();
	}
	
}
