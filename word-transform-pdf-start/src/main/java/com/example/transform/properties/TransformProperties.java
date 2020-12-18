package com.example.transform.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


/**
 * 转换属性定义
 *
 * @author Administrator
 * @date 2020/12/18
 */
@Component
@ConfigurationProperties(prefix = "spring.transform")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Primary
public class TransformProperties {
	
	/**
	 * 字体路径
	 */
	private String fontPath;
	
	
}
