package com.imooc.concurrency.Example.aqs;

import com.imooc.concurrency.TestController;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @program: concurrency
 * @description:
 * 保证同一时刻并发访问的线程数目, 用于仅能提供有限个数的访问资源, 比如数据库的有效连接.
 *
 * --获取多个许可? 释放多个许可?
 * --并发数是3而同一时间又获得了3个许可,相当于同一时间只能执行一个test()的调用了,一次性就拿了3个许可,同一秒已经没有多余的许可释放了.跟单线程很像,只不过得等这3个许可都执行完,下一个才能执行.
 *
 *  如果并发特别多,需要丢弃一部分许可:semaphore.tryAcquire()尝试获取一个许可.未得到许可的丢弃.参考SemaphoreExample02
 *
 * @author: Karl Guo
 * @create: 2018-10-11 16:16
 **/
@Slf4j
public class SemaphoreExample01 {

    private static Integer threadCount = 20;

    private static final Semaphore semaphore = new Semaphore(3);


    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        //将20个线程全部放入线程池中,但是每一时刻只能执行3个
        for (int i = 0; i < threadCount; i++){
            final Integer threadNum = i;
            executor.execute(() -> {
                try {
                    semaphore.acquire();   //获取一个许可
//                    semaphore.acquire(3);   //获取多个许可

                    test(threadNum);

                    semaphore.release();    //释放一个许可
//                    semaphore.release(3);   //释放多个许可

                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
            });
        }
        executor.shutdown();

    }

    private static void test(Integer threadNum) throws InterruptedException {
        log.info("threadNum = {}", threadNum);
        Thread.sleep(1000);
    }


}
