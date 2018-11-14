package com.imooc.concurrency.Example.cache;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @program: concurrency
 * @description:
 *
 *  redis的学习网址: http://redis.cn
 *
 * @author: Karl Guo
 * @create: 2018-11-14 19:38
 **/

@Component
public class RedisClient {

    @Resource(name = "redisPool")
    private JedisPool jedisPool;

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            //记得在使用完之后要归还连接
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            //记得在使用完之后要归还连接
            if (jedis != null) {
                jedis.close();
            }
        }
    }


}
