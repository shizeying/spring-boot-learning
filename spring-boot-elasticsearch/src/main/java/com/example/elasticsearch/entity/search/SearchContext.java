package com.example.elasticsearch.entity.search;

import java.io.Serializable;

/**
 * 搜索上下文
 *
 * @author shizeying
 * @date 2021/01/02
 */
public class SearchContext  implements Serializable {
	SearchResultBean searchResultBean;
	
	QueryRequest queryRequest;
	
	//SearchTemplate searchTemplate;
	
	//RankFunction rankFunction;
	
	//PdDocument semantic;
	
	//是否在缓存中找到
	Boolean findInCache = false;
	
	Boolean isCompositeIndex;
	
	public SearchContext(QueryRequest queryRequest) {
		
		this.queryRequest = queryRequest;
		
		this.searchResultBean = new SearchResultBean();
		this.searchResultBean.setKw(queryRequest.getKw());
		this.searchResultBean.setTt(queryRequest.getTt());
		this.searchResultBean.setId(queryRequest.getId());
		this.searchResultBean.setCustomQuery(queryRequest.getCustomQuery());
		this.searchResultBean.setAggregationSize(queryRequest.getAggregationSize());
		//this.semantic = new PdDocument();
		
	}
}
