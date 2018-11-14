package com.imooc.concurrency.Example.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-11-14 19:30
 **/

@Configuration
public class RedisConfig {

    @Bean(name = "redisPool")
    public JedisPool jedisPool(@Value("${redis.host}") String host, @Value("${redis.port}") Integer port) {
        return new JedisPool(host, port);
    }


}
