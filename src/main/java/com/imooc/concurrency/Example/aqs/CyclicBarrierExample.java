package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: concurrency
 * @description: 也是一个同步辅助类，它允许一组线程相互等待，直到到达某个公共的屏障点（循环屏障）
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

//    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    //3.在初始话得时候设置runnable,当线程达到屏障时(await数量等于5时)优先执行runnable
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5 , () -> {
       log.info("callback is running");
    });

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
        executor.shutdown();
    }


    private static void test(Integer threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} ready", threadNum);
        //1.每个线程都持续等待
        cyclicBarrier.await();
        //2.每个线程都只等待固定的时间,然后释放
//        cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
        log.info("{} continue", threadNum);
    }


}
