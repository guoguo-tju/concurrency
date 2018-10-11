package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: concurrency
 * @description: CountDownLatch，通过给定的计数器数值为其初始化，该计数器是原子性操作，保证同时只有一个线程去操作该计数器。
 * 调用该类await方法的线程会一直处于阻塞状态。只有其他线程调用countDown方法（每次使计数器-1）,使计数器归零才能继续执行。
 * @author: Karl Guo
 * @create: 2018-10-09 17:39
 **/
@Slf4j
public class CountDownLatchExample {

    private static Integer threadCount = 500;

    private static CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < threadCount; i++) {
            //传入线程池的变量应该由final修饰
            final int threadNum = i;
            executor.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        //await方法的线程会一直处于阻塞状态,当计数器归零才能继续执行
        //countDownLatch.await();

        //设置等待的时间，主线程到这里先阻塞,如果超过此时间，则主线程不继续等待,往下执行
        //(用在某些复杂的功能:要给定执行时间,超过这个时间就算没执行完也要接着往下执行的情况,但是之前的其他多线程也会执行完)
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        log.info("finish");
        //使用完毕要关闭线程池(不是立刻关闭,而是等所有线程都执行完才关闭)
        executor.shutdown();
    }

    private static void test(int i) throws InterruptedException {
        Thread.sleep(100);
        log.info("i={}", i);
    }


}
