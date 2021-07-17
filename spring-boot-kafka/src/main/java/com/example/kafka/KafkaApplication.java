package com.example.kafka;

import com.example.utils.ReadResourcesUtils;
import com.example.utils.config.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.*;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.core.*;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@EnableConfigurationProperties
@EnableKafka
@SpringBootApplication
@SuppressWarnings("ALL")
public class KafkaApplication {
	
	public static void main(String[] args) throws IOException {
		final ConfigurableApplicationContext run = SpringApplication.run(KafkaApplication.class, args);
		String topic =
				//"jnpc2";
		"twittercomment2";
		//"twitterfans2";
		//"twitteruser2";
		//"twitterpost2";
		//"twitterfans2";
		String xlsx =
				"twitterComment.xlsx";
		//"twitterPost.xlsx";
		//"twitterUser.xls";
		final KafkaTemplate kafkaTemplate = run.getBean(KafkaTemplate.class);
		final Supplier<Stream<Map<String, Object>>> supplier = ReadResourcesUtils.getResourceExcelMapListSupplier(
				"xlsx/"+xlsx, 0);
		supplier.get().forEach(map -> kafkaTemplate.send(topic, JacksonUtil.bean2Json(map)));
		
		
		//final LineIterator lineIterator = FileUtils.lineIterator(
		//		FileUtils.getFile("/Users/shizeying/IdeaProjects/spring-boot-learning/spring-boot-kafka/src/main/resources/xlsx" +
		//				                  "/tolz_formated_1000.json"));
		//
		//while (lineIterator.hasNext()) {
		//	final String s = lineIterator.nextLine();
		//
		//	final JsonNode jsonNode = JacksonUtil.readJson(s);
		//	System.err.printf("输出的json:【%s】", jsonNode.toPrettyString());
		//
		//	kafkaTemplate.send("jnpc2", jsonNode.toPrettyString());
		//
		//
		//}
		//
		run.close();
		
	}
	
}
