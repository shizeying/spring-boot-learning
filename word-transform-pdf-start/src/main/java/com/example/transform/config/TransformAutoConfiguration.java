package com.example.transform.config;


import com.example.transform.Enum.EPlatform;
import com.example.transform.domain.OSInfo;
import com.example.transform.properties.TransformProperties;
import com.example.transform.service.TransformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.vavr.API.*;

@ConditionalOnClass(TransformService.class) //如果存在DemoService类才加载此配置文件
@EnableConfigurationProperties(TransformProperties.class)
@Configuration
public class TransformAutoConfiguration {
	@Autowired
	private TransformProperties transformProperties;
	
	
	/**
	 * 判断DemoService 类是否已经存在实现类，如果存在则忽略此bean 的注入
	 *
	 * @return {@link TransformService}
	 */
	@Bean
	@ConditionalOnMissingBean(TransformService.class)
	public TransformService getTransformProperties() {
		transformProperties = transformProperties.getFontPath() == null ? transformProperties.setFontPath(getFontPath()) : transformProperties;
		return new TransformService(transformProperties);
	}
	
	private String getFontPath() {
		return Match(OSInfo.getOSName())
				       .of(
						       Case($(EPlatform.LINUX), run -> "/usr/share/fonts/"),
						       Case($(EPlatform.LINUX), run -> "/System/Library/Fonts"),
						       Case($(), run -> "C:/WINDOWS/Fonts"));
	}
}
