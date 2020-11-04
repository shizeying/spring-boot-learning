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
			
			entity.setAbs("adf");
			entity.setEndTime(new Date());
			entity.setEntityTag("dsaf"+i);
			entity.setEntityType("abc");
			entity.setGisAdress("daf");
			entity.setGisAdress("dafas");
			entity.setLat(213.0);
			entity.setLon(123.0);
			entity.setName("zhangda");
			entity.setStartTime(new Date());
			entity.setOrigin("zhangsan");
			// 调用消息发送类中的消息发送方法
			sender.send(entity);
		}
	}

}
