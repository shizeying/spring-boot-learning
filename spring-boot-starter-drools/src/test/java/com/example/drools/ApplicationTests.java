package com.example.drools;

import com.drools.core.*;
import com.example.drools.entity.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.kie.api.runtime.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

@SpringBootTest
@Slf4j
class ApplicationTests {
	
	
	// 1.注入KieTemplate
	@Autowired
	private KieTemplate kieTemplate;
	
	@Test
	void contextLoads() {
	}
	
	// 2.获取指定的规则文件，生成KieSession
	@Test
	public void test01() {
		KieSession session = kieTemplate.getKieSession("rule.drl");
		Person person = new Person();
		person.setName("王五");
		session.setGlobal("isEnable", true);
		session.setGlobal("log", log);
		session.insert(person);
		session.fireAllRules();
		log.info("person=>{}", person);
	}
	
	
}
