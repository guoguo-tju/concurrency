package com.imooc.concurrency.Example.commonUnsafe;

import com.imooc.concurrency.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @program: concurrency
 * @description:
 *      jodaTime类, 线程安全 , 推荐使用 (需导包)
 * @author: Karl Guo
 * @create: 2018-09-30 15:39
 **/
@Slf4j
@ThreadSafe
public class DateFormatExample02 {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");

    //线程总访问量
    public static final Integer clientTotal = 5000;
    //线程的并发数
    public static final Integer threadTotal = 20;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        //Semaphore设置信号量
        Semaphore semaphore = new Semaphore(threadTotal);
        //计数器闭锁
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            //传入线程池的值一定是fina类型的.
            final int count = i;
            executorService.execute(() -> {
                try {
                    //当超过设置的并发数时,会被阻塞
                    semaphore.acquire();
                    update(count);
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
    }

    public static void update(int count) {
        Date date = DateTime.parse("20180930", dateTimeFormatter).toDate();
        log.info("{},{}", count , date);
    }

}
