package com.imooc.concurrency.Example.commonUnsafe;

import com.imooc.concurrency.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @program: concurrency
 * @description:
 *  SimpleDateFormate类, 线程不安全
 *   要想其线程安全,需要在方法内部,new SimpleDateFormat("")对象
 * @author: Karl Guo
 * @create: 2018-09-30 15:39
 **/
@Slf4j
@ThreadSafe
public class DateFormatExample01 {

//    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    //线程总访问量
    public static final Integer clientTotal = 5000;
    //线程的并发数
    public static final Integer threadTotal = 20;

    public static Integer count = 0;

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
                    update();
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

    public static void update() {
        try {
            //将simpleDateFormat放在方法内,每次使用就声明一个新的对象,就线程安全了
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            simpleDateFormat.parse("20180930");
        } catch (ParseException e) {
            log.error("parse exception", e);
        }
    }

}
