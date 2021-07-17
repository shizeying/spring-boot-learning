package com.example.config;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.data.redis.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import redis.clients.jedis.*;


/**
 * @author <a href="MAILTO: w741069229@gmail.com">shizeying</a>
 * @version 1.0.0
 * @des {@link JedisConfig} 能配置
 * @date 2020/11/07
 * @since 1.0.0
 */
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
