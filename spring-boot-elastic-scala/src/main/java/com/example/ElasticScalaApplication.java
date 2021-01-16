package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.xml.ws.soap.MTOM;

/**
 * 弹性scala应用程序
 *
 * @author shizeying
 * @date 2021/01/13
 */
@SpringBootApplication
@MapperScan("com.example.mapper")
@EnableConfigurationProperties
public class ElasticScalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticScalaApplication.class, args);
	}

}
