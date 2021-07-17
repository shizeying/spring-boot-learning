package com.example.config;

import org.apache.commons.compress.utils.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.web.servlet.config.annotation.*;

import java.util.*;
import java.util.concurrent.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	public static List<String> taskIds = Lists.newArrayList();
	
	public static final String ASYNC_KEY = "async01-";
	
	/**
	 * 声明一个线程池
	 *
	 * @return 执行器
	 */
	@Bean("executorBeanName")
	public ThreadPoolTaskExecutor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
		//1: 核心线程数目
		executor.setCorePoolSize(4);
		//2: 指定最大线程数,只有在缓冲队列满了之后才会申请超过核心线程数的线程
		executor.setMaxPoolSize(10);
		//3: 队列中最大的数目
		executor.setQueueCapacity(200);
		//5:当pool已经达到max size的时候，如何处理新任务
		// CallerRunsPolicy: 会在execute 方法的调用线程中运行被拒绝的任务,如果执行程序已关闭，则会丢弃该任务
		// AbortPolicy: 抛出java.util.concurrent.RejectedExecutionException异常
		// DiscardOldestPolicy: 抛弃旧的任务
		// DiscardPolicy: 抛弃当前的任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		//6: 线程空闲后的最大存活时间(默认值 60),当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
		executor.setKeepAliveSeconds(60);
		//7:线程空闲时间,当线程空闲时间达到keepAliveSeconds(秒)时,线程会退出,直到线程数量等于corePoolSize,如果allowCoreThreadTimeout=true,则会直到线程数量等于0
		executor.setAllowCoreThreadTimeOut(false);
		executor.setThreadNamePrefix(WebMvcConfig.ASYNC_KEY);
		executor.initialize();
		return executor;
	}
	
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setTaskExecutor(asyncExecutor());
	}
	
}
