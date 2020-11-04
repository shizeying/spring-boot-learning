package com.example.kafka;

import com.example.kafka.entity.TCommonEntity;
import com.example.kafka.service.ProducerService;
import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;

@EnableConfigurationProperties
@EnableKafka
@SpringBootApplication
@SuppressWarnings("ALL")
public class KafkaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =	SpringApplication.run(KafkaApplication.class, args);
		ProducerService sender = context.getBean(ProducerService.class);
		
		for (int i = 0; i <=10;i++){
			TCommonEntity entity = new TCommonEntity();
			entity.setReId("12dfaf3");
			
		
		
			// 调用消息发送类中的消息发送方法
			sender.send(entity);
		}
	}

}
