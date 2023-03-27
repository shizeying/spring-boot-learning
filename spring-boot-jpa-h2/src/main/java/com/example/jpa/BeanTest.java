package com.example.jpa;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanTest implements ApplicationContextAware,BeanPostProcessor,DisposableBean {
		
		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName)
				throws BeansException {
				System.err.println(String.format("执行postProcessBeforeInitialization：「%s」", beanName));
				return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
		}
		
		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName)
				throws BeansException {
				System.err.println(String.format("执行postProcessAfterInitialization：「%s」", beanName));
				return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
		}
		
		@Override
		public void destroy() throws Exception {
				System.err.println("DisposableBean:destroy");
		}
		
		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		}
}