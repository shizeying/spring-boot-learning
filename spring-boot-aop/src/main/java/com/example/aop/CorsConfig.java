package com.example.aop;

import com.example.config.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import org.springframework.web.filter.*;

/**
 * @author shizeying
 * @version 1.0.0
 * @doc
 * @desc {@link CorsConfig}  跨域配置
 * @date 2020/11/04
 */
@Slf4j
@SuppressWarnings("ALL")
@Configuration(proxyBeanMethods = false)
public class CorsConfig {
	@Autowired
	private DepartmentProperties departmentProperties;
	
	@Bean
	public CorsFilter corsFilter() {
		System.out.println("map:" + departmentProperties.getDepartmentMap());
		//配置初始化对象
		CorsConfiguration configuration = new CorsConfiguration();
		//允许跨域的域名，如果要携带cookie，不能写* 。  *：代表所有的域名都可以访问
		configuration.addAllowedOrigin("*");
		configuration.setAllowCredentials(true);
		configuration.addAllowedMethod("*");            //代表所有的请求方法
		configuration.addAllowedHeader("*");        //允许携带任何头信息
		
		//初始化cors配置源对象
		UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
		configurationSource.registerCorsConfiguration("/**", configuration);
		
		//返回corsFilter实例，参数:cors配置源对象
		return new CorsFilter(configurationSource);
		
		
	}
}
