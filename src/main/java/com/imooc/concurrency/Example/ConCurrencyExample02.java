package com.imooc.concurrency.Example;

import com.imooc.concurrency.annotation.ThreadSafe;
import com.imooc.concurrency.annotation.UnThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class ConCurrencyExample02 {

    //线程总访问量
    public static final Integer clientTotal = 5000;
    //线程的并发数
    public static final Integer threadTotal = 20;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        //Semaphore设置信号量
        Semaphore semaphore = new Semaphore(threadTotal);
        //计数器闭锁
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    //当超过设置的并发数时,会被阻塞
                    semaphore.acquire();
                    add();
                    //执行完之后要释放
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
            });
        }
        //当执行完所有并发后,才唤醒主线程
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        log.info("count:{}", count.get());

    }

    public static void add() {
        count.incrementAndGet();
//        count.getAndIncrement();
    }

}
