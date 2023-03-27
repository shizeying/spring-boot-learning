package com.example.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
@SpringBootApplication
public class SpringBootJpaH2Application {
	
	public static void main(String[] args) throws Exception {
			final ConfigurableApplicationContext run = SpringApplication.run(
					SpringBootJpaH2Application.class, args);
			final BeanTest bean = run.getBean(BeanTest.class);
			bean.destroy();
	}
	
}