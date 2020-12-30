package com.example.typehandle;

import com.example.typehandle.annotation.ToMap;
import com.example.typehandle.entity.UsersEntity;
import com.example.typehandle.service.UsersService;
import com.example.typehandle.utils.BeanUtils;
import com.example.utils.config.JacksonUtil;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class SpringBootMybatisPlusApplicationTests {
	@Autowired
	private UsersService usersService;
	@Autowired
	private BeanUtils beanUtils;
	
	@Test
	void contextLoads() {
	}
	@Test
	void setUserService() throws IllegalAccessException {
		UsersEntity usersEntity = new UsersEntity();
		usersEntity.setId(1L);
		usersEntity.setName("teset");
		Map<String, Object> map = beanUtils.javaBean2Map(usersEntity, ToMap.class);
		System.out.println(map);
	}
	

	
}
