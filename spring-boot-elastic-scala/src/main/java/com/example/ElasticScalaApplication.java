package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 弹性scala应用程序
 *
 * @author shizeying
 * @date 2021/01/13
 */
@SpringBootApplication
@EnableConfigurationProperties
public class ElasticScalaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ElasticScalaApplication.class, args);
	}
	
}
