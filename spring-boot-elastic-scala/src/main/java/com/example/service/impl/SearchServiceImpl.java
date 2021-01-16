package com.example.service.impl;

import com.example.service.SearchService;
import com.example.service.TConfigService;
import com.example.utils.SingleData;
import com.sksamuel.elastic4s.ElasticClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private ElasticClient client;
	@Autowired
	private TConfigService tConfigService;
	@Override
	public void searchTest() {
	tConfigService.getAllSearchChineseColumns("普通关系");
		
		
		
		
		
	}
	
	/**
	 * 构建器通用搜索
	 */
	@Override
	public void builderGenericSearch() {
	
	}
}
