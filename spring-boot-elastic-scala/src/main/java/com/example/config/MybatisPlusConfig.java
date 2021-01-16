package com.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;


/**
 * mybatis +配置
 *
 * @author shizeying
 * @date 2020/12/21
 */
@MapperScan("ai.plantdata.analysis.search.mapper")
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
	/**
	 * 分页插件
	 */
	/**
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
	
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setUseDeprecatedExecutor(false);
	}
	
	
	@Bean
	public EasySqlInjector easySqlInjector() {
		return new EasySqlInjector();
	}
	
	/**
	 * @author <a href="MAILTO: w741069229@gmail.com">shizeying</a>
	 * @version 1.0.0
	 * @des {@link EasySqlInjector} 自定义数据方法注入
	 * @date 2020/12/05
	 * @since 1.0.0
	 */
	public static class EasySqlInjector extends DefaultSqlInjector {
		
		@Override
		public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
			//防止父类方法不可用
			List<AbstractMethod> methods = super.getMethodList(mapperClass);
			methods.add(new InsertBatchSomeColumn());
			return methods;
		}
	}
}

