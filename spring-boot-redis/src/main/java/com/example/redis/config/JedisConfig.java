package com.example.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Component
@Slf4j
public class JedisConfig {
	
	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public JedisPool jedisProvider() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
		config.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
		config.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
		config.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
		JedisPool jedisPool = new JedisPool(config, redisProperties.getHost(),
				redisProperties.getPort(),
				redisProperties.getTimeout().getNano()
				, redisProperties.getPassword());
		
		log.info("JedisPool生成成功！");
		log.info("Redis地址：{}:{}", redisProperties.getHost(), redisProperties.getPort());
		
		return jedisPool;
	}
}
