package com.example.config;

import com.example.properties.TusProperties;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.download.DownloadGetRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@EnableConfigurationProperties(TusProperties.class)
@Configuration
@ConditionalOnProperty//存在对应配置信息时初始化该配置类
		(
				prefix = "large",//存在配置前缀hello
				value = "enabled",//开启
				matchIfMissing = true//缺失检查
		)
public class TusAutoConfiguration {
	@Autowired
	private TusProperties tusProperties;
	@Bean//创建HelloService实体bean
	@ConditionalOnMissingBean(TusFileUploadService.class)//缺失HelloService实体bean时，初始化HelloService并添加到SpringIoc
	public TusFileUploadService tusFileUploadService()
	{
		
		return new TusFileUploadService().withStoragePath(tusProperties.getTusUploadDirectory())
		                                 
		                                 .withDownloadFeature();
	}
	

	@Bean
	@ConditionalOnMissingBean(DownloadGetRequestHandler.class)
	public DownloadGetRequestHandler downloadGetRequestHandler(){
		return  new DownloadGetRequestHandler();
	}
	
}
