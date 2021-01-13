package com.example.service.impl;

import com.example.config.EsUtils;
import com.example.config.HighlightEntity;
import com.example.service.SearchService;
import com.google.common.collect.Lists;
import com.sksamuel.elastic4s.ElasticClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private ElasticClient elasticClient;
	
	@Override
	public void searchTest() {
		List<HighlightEntity> list = Lists.newArrayList();
		EsUtils.builderHighlight(list);
	}
}
