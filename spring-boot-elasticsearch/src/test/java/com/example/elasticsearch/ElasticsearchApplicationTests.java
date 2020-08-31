package com.example.elasticsearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
class ElasticsearchApplicationTests {

	/**
	 * @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
	 * 由于AbstractElasticsearchConfiguration中已经注入了elasticsearchOperations和
	 * elasticsearchTemplate，本质上这两个函数中的方法并没有什么不同，所以直接使用elasticsearchTemplate
	 * 就可以了
	 */
	@Autowired
	private ElasticsearchRestTemplate template;



	@Test
	void contextLoads() {
	}
	@Test
	void setTemplate(){

		//template
	}
	@Test
	void  setClient(){

	}


}
