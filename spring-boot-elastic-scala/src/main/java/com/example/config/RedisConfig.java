//package com.example.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.lang.reflect.Method;
//import java.time.Duration;
//
///**
// * @author shizeying
// */
//@Slf4j
//@Configuration
//public class RedisConfig {
//	public final static String KG_SEARCH_REDIS = "kgcloud-kgsearch:query";
//	@Autowired
//	private ObjectMapper mapper;
//
//	/**
//	 * 申明缓存管理器，会创建一个切面（aspect）并触发Spring缓存注解的切点（pointcut）
//	 * 根据类或者方法所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，将数据添加到缓存之中或者从缓存中移除某个值
//	 *
//	 * @return
//	 */
//	@Bean
//	public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
//		log.info("加载redis");
//		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//		// 配置序列化
//		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//		                                                                         .serializeKeysWith(RedisSerializationContext.SerializationPair
//				                                                                                            .fromSerializer(redisSerializer))
//		                                                                         .serializeValuesWith(RedisSerializationContext.SerializationPair
//				                                                                                              .fromSerializer(
//						                                                                                              jackson2JsonRedisSerializer))
//
//		                                                                         // 设置过期时间 10 分钟
//		                                                                         .entryTtl(Duration.ofMinutes(10))
//
//		                                                                         .prefixCacheNameWith(KG_SEARCH_REDIS)
//		                                                                         // 禁止缓存 null 值
//		                                                                         .disableCachingNullValues()
//		                                                                         // 设置 key 序列化
//		                                                                         .serializeKeysWith(keyPair())
//		                                                                         // 设置 value 序列化
//		                                                                         .serializeValuesWith(valuePair());
//		RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
//		                                                  .cacheDefaults(redisCacheConfiguration)
//		                                                  .build();
//		return cacheManager;
//	}
//
//	/**
//	 * 配置键序列化
//	 *
//	 * @return StringRedisSerializer
//	 */
//	private RedisSerializationContext.SerializationPair<String> keyPair() {
//		return RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
//	}
//
//	/**
//	 * 配置值序列化，使用 GenericJackson2JsonRedisSerializer 替换默认序列化
//	 *
//	 * @return GenericJackson2JsonRedisSerializer
//	 */
//	private RedisSerializationContext.SerializationPair<Object> valuePair() {
//		return RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
//	}
//
//	@Bean
//	public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
//		log.info("加载RedisTemplate");
//		// 创建一个模板类
//		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//		// 将刚才的redis连接工厂设置到模板类中
//		template.setConnectionFactory(factory);
//		// 设置key的序列化器
//		template.setKeySerializer(new StringRedisSerializer());
//		// 设置value的序列化器
//		//使用Jackson 2，将对象序列化为JSON
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//		//json转对象类，不设置默认的会将json转成hashmap
//		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
//				ObjectMapper.DefaultTyping.NON_FINAL,
//
//				JsonTypeInfo.As.WRAPPER_ARRAY);
//		jackson2JsonRedisSerializer.setObjectMapper(mapper);
//		template.setValueSerializer(jackson2JsonRedisSerializer);
//
//		return template;
//	}
//
//	/**
//	 * 自定义 Cache 的 key 生成器
//	 */
//	@Bean("oneKeyGenerator")
//	public KeyGenerator getKeyGenerator() {
//		return new KeyGenerator() {
//			@Override
//			public Object generate(Object obj, Method method, Object... objects) {
//				return KG_SEARCH_REDIS + method.getName();
//			}
//		};
//	}
//
//}
//
//
