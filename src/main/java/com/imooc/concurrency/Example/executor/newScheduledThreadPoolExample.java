package com.imooc.concurrency.Example.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @program: concurrency
 * @description: 定长线程池，支持定时和周期,延迟任务执行
 * @author: Karl Guo
 * @create: 2018-10-25 11:43
 **/
@Slf4j
public class newScheduledThreadPoolExample {

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public static void main(String[] args)  {

/*        //1. schedule方法
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("schedule run...");
            }
        }, 3, TimeUnit.SECONDS);  //延迟3s执行

        executorService.shutdown();
       */



/*
        //2.  scheduleAtFixedRate方法: 初始延迟1s执行,然后每3s为周期执行. 不要用shutDown()
        //若任务计算时间大于周期,则等任务执行完成后再执行下一次任务
        log.info("main begin...");
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("schedule run...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 3, TimeUnit.SECONDS);
*/

        //3.  scheduleWithFixedDelay方法: 初始延迟0s执行,然后等上次任务执行完成后延迟1s周期执行. 不要用shutDown()
        log.info("main begin...");
        for (int i = 0; i < 2; i++) {
            Runnable task = () -> {
                try {
                    Thread.sleep(5000);
                    log.info("schedule run...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executorService.scheduleWithFixedDelay(task, 3, 1, TimeUnit.SECONDS);
        }

    }
}
