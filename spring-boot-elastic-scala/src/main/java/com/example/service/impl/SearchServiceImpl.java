package com.example.service.impl;

import com.example.service.SearchService;
import com.sksamuel.elastic4s.ElasticClient;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private ElasticClient elasticClient;
	
	@Override
	public void searchTest() {
	
	}
}
