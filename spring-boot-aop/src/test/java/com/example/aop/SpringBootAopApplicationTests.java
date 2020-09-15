package com.example.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootAopApplicationTests {

	@Test
	void contextLoads() {
	}

  public static void main(String[] args) {
		Map<String, Integer> lookup =
				new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
	
		lookup.put("One", 1);
		lookup.put("tWo", 2);
		lookup.put("thrEE", 3);
	
		System.out.println(lookup.get("Two"));
		System.out.println(lookup.get("three"));
	}//
}
