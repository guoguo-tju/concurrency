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
public class RateLimiterExample1 {

    // 限流5个/s ,令牌桶算法,相当于没0.2s生成一个令牌可以被获取
    private static RateLimiter rateLimiter = RateLimiter.create(5);

    public static void main(String[] args) throws InterruptedException {

        for (int index = 0; index < 100; index++) {
         /*
            Thread.sleep(100);   // 相当于每秒有10个请求
            // 只有5/s能被打印
            if (rateLimiter.tryAcquire()){
                handle(index);
            }*/

            //等待100毫秒来获取一个令牌,但是200毫秒才生成,所以只能获取到最初始的请求
            if (rateLimiter.tryAcquire(100, TimeUnit.MILLISECONDS)) {
                handle(index);
            }

            //等待200毫秒来获取一个令牌,就每个请求都能执行到了,也就是只有>200就都能执行到
            if (rateLimiter.tryAcquire(200, TimeUnit.MILLISECONDS)) {
                handle(index);
            }

        }

    }


    private static void handle(int index) {
        log.info("{}", index);
    }


}
