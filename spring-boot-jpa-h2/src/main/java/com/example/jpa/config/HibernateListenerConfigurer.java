package com.example.jpa.config;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Configuration;

/**
 * hibernate侦听器配置 开启jpa动态插入：只对非空进行修改
 *
 * @author shizeying
 * @author: Jonny
 * @description:
 * @date:created in 2021/4/27 12:19
 * @modificed by:
 * @date 2021/11/17
 */
@Configuration
public class HibernateListenerConfigurer {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	protected void init() {
		SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry registry = sessionFactory.getServiceRegistry()
				.getService(EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.MERGE).clearListeners();
		registry.getEventListenerGroup(EventType.MERGE)
				.prependListener(IgnoreNullEventListener.INSTANCE);
	}
	
}
