package com.example.typehandle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.typehandle.dao")
@SpringBootApplication
public class SpringBootMybatisPlusApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisPlusApplication.class, args);
	}
	
}
