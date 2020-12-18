package com.example.transform;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.example.transform.properties.TransformProperties;
import com.example.transform.service.TransformService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class WordTransformPdfStartApplicationTests {
	
	@Autowired
	
	private TransformProperties properties;
	@Autowired
	private TransformService transformService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void setPlatform() {
		System.out.println(properties.getFontPath());
		System.out.println(transformService.hello());
	}
	
	@Test
	void setProperties() throws InterruptedException {
		File wordFile = new File("E:\\software\\IdeaProjects\\spring-boot-learning\\word-transform-pdf-start\\src\\main\\resources\\情报分析平台概要设计_v1.2(1)(2).docx"), target = new File("test.pdf");
		IConverter converter = LocalConverter.builder()
				                       .baseFolder(new File("E:\\software\\IdeaProjects\\spring-boot-learning\\word-transform-pdf-start\\src\\main\\resources\\"))
									   
				                       .workerPool(20, 25, 2, TimeUnit.SECONDS)
				                       .processTimeout(5, TimeUnit.SECONDS).build();
		
		Future<Boolean> conversion = converter.convert(wordFile)
				                             .as(DocumentType.DOCX)
				                             .to(target)
											 
				                             .as(DocumentType.PDF)
											 
				                             // .prioritizeWith(1000) // optional
				                             .schedule();
		conversion.isDone();
		Thread.sleep(500000);
		
	}
	
}
