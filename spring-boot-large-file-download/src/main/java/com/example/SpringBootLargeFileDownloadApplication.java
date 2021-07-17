package com.example;

import org.springframework.boot.autoconfigure.*;

import java.util.*;

@SpringBootApplication
public class SpringBootLargeFileDownloadApplication {
	public static final String TW_PER_ORG = "tw_人物_机构_";
	public static final String TB_CHARACTER = "tb_character_";
	public static final String TB_ORG = "tb_org_";
	public static final String ORG = "机构_";
	
	public static void main(String[] args) {
		String name = "tw_人物_机构_212312";
		switch (name.replaceAll("\\d", "")) {
			case TW_PER_ORG:
				System.out.println("b");
				break;
			case TB_CHARACTER:
				System.out.println("a");
				break;
			case TB_ORG:
				break;
			case ORG:
				break;
			default:
				throw new NoSuchElementException("未匹配到对应的模版");
			
		}
		
		//SpringApplication.run(SpringBootLargeFileDownloadApplication.class, args);
	}
	
}
