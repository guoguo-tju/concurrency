package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: concurrency
 * @description:
 * 也是一个同步辅助类，它允许一组线程相互等待，直到到达某个公共的屏障点（循环屏障）
 * 通过它可以完成多个线程之间相互等待，只有每个线程都准备就绪后才能继续往下执行后面的操作。
 * 每当有一个线程执行了await方法，计数器就会执行+1操作，待计数器达到预定的值，所有的线程再同时继续执行。由于计数器释放之后可以重用（reset方法），所以称之为循环屏障。
 * 与CountDownLatch区别：
 * 1、计数器可重复用
 * 2、描述一个或多个线程等待其他线程的关系/多个线程相互等待
 * @author: Karl Guo
 * @create: 2018-10-15 18:12
 **/
@Slf4j
public class CyclicBarrierExample {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            final Integer threadNum = i;
            executor.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private static void test(Integer threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} ready", threadNum);
        cyclicBarrier.await();
        log.info("{} continue" , threadNum);
    }


}
