package com.imooc.concurrency.Example.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-11-20 19:48
 **/

@Slf4j
public class RateLimiterExample2 {

    private static RateLimiter rateLimiter = RateLimiter.create(5);

    public static void main(String[] args) throws Exception {

        for (int index = 0; index < 100; index++) {
            //同步等待线程直到拿到下一个令牌为止.
            //因此可以保证所有请求都被处理,但会按照一定的速率
            rateLimiter.acquire();
            handle(index);
        }

    }


    private static void handle(int index) {
        log.info("{}", index);
    }


}
