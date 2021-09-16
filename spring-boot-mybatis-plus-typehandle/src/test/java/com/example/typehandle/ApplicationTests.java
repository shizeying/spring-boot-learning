package com.example.typehandle;

import com.example.typehandle.entity.TestUsersEntity;
import com.example.typehandle.service.TestUsersService;
import com.example.typehandle.utils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
	@Autowired
	private TestUsersService testUsersService;
	@Autowired
	private BeanUtils beanUtils;
	
	@Test
	void contextLoads() {
	}
	@Test
	void setUserService()  {
		TestUsersEntity testUsersEntity = testUsersService.find().get(0);
		System.out.println(testUsersEntity.getName());
		//Map<String, Object> map = beanUtils.javaBean2Map(testUsersEntity, ToMap.class);
		//System.out.println(map);
	}
	

	
}
