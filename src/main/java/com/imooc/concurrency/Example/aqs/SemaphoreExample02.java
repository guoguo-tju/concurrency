package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @program: concurrency
 * @description:
 * 保证同一时刻并发访问的线程数目, 用于仅能提供有限个数的访问资源, 比如数据库的有效连接.
 *  如果并发特别多,需要丢弃一部分许可:
 *  1.semaphore.tryAcquire()尝试获取一个许可.未得到许可的丢弃.
 *
 *  2.semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)  尝试获取许可一段时间，未得到许可的线程丢失
 *      参数1：等待时间长度  参数2：等待时间单位
 *
 * @author: Karl Guo
 * @create: 2018-10-11 16:16
 **/
@Slf4j
public class SemaphoreExample02 {

    private static Integer threadCount = 20;

    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        //将20个线程全部放入线程池中,但是每一时刻只能执行3个
        for (int i = 0; i < threadCount; i++){
            final Integer threadNum = i;
            executor.execute(() -> {
                try {
                    //1.semaphore.tryAcquire()尝试获取一个许可,未得到许可的线程丢失
                    if (semaphore.tryAcquire()){

                    //2.尝试获取许可一段时间，未得到许可的线程丢失
//                  if(semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)){
                        test(threadNum);
                        semaphore.release();    //释放一个许可
                    }
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
