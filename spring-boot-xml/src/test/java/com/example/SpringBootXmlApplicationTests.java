package com.example;

import com.example.entity.Namespace;
import com.example.entity.SqlBean;
import com.example.entity.XmlBean;
import com.example.service.StringJoinService;
import com.example.utils.XmlFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootTest
class SpringBootXmlApplicationTests {

	
	@Test
	void contextLoads() {
	
	}

	@Test
	void setXml() {
		String namespace="<mapper namespace=\"com.example.typehandle.dao.UsersMapper\" >";
		String baseMap="<resultMap id=\"BaseResultMap\" type=\"com.example.typehandle.entity.UsersEntity\">";
		String id="<id column=\"id\" property=\"id\"/>";
		XmlBean xmlBean = new XmlBean("abc", "abc", "com");
		SqlBean sqlBean = new SqlBean();
		sqlBean.setId("select");
		//sqlBean.setResultType("baseresulttyp");
		System.out.println(sqlBean.toString());
		
		
	}
	@Autowired
	private XmlFormatter xmlFormatter;
	@Test
	void  setFormat(){
		Namespace namespace = new Namespace();
		// 等待格式化的XML
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><PARAM><DBID>35</DBID><SEQUENCE>atgtca</SEQUENCE><MAXNS>10</MAXNS><MINIDENTITIES>90</MINIDENTITIES><MAXEVALUE>10</MAXEVALUE><USERNAME>admin</USERNAME><PASSWORD>111111</PASSWORD><TYPE>P</TYPE><RETURN_TYPE>2</RETURN_TYPE></PARAM>";//未格式化前的xml
		
		// 格式化结果
		System.out.println(xmlFormatter.format(namespace.toString()));
	}
	
	@Autowired
	private StringJoinService stringJoinService;
	@Test
	void  setStringJoinService() throws InterruptedException {
		String xml = stringJoinService.setAddJoin(new Namespace());
		stringJoinService.writeFile(xml,"/Users/shizeying/Downloads/file.xml");
	}
}
