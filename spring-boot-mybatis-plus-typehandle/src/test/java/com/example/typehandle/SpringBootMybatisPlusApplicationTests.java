package com.example.typehandle;

import com.example.typehandle.service.UsersService;
import com.example.utils.config.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMybatisPlusApplicationTests {
	@Autowired
	private UsersService usersService;
	
	@Test
	void contextLoads() {
	}
	@Test
	void setUserService(){
		System.out.println(JacksonUtil.bean2Json(usersService.find()));
	}
	
}
