package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;


/**
 * @program: concurrency
 * @description: 它控制锁有三种模式（写、读、乐观读）。一个StempedLock的状态是由版本和模式两个部分组成。
 * 锁获取方法返回一个数字作为票据（stamp），他用相应的锁状态表示并控制相关的访问。数字0表示没有写锁被锁写访问，
 * 在读锁上分为悲观锁和乐观锁。
 * <p>
 * 乐观读：
 * 如果读的操作很多写的很少，我们可以乐观的认为读的操作与写的操作同时发生的情况很少，因此不悲观的使用完全的读取锁定。
 * 程序可以查看读取资料之后是否遭到写入资料的变更，再采取之后的措施。
 * @author: Karl Guo
 * @create: 2018-10-18 14:53
 **/
@Slf4j
public class StempedLockExample {

    private static StampedLock stampedLock = new StampedLock();

    //线程总访问量
    public static final Integer clientTotal = 5000;
    //线程的并发数
    public static final Integer threadTotal = 20;

    public static Integer count = 0;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        //Semaphore设置信号量
        Semaphore semaphore = new Semaphore(threadTotal);
        //计数器闭锁,目的是保证所有的请求都处理完才会输出结果
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
//        countDownLatch.await(12, TimeUnit.MILLISECONDS);
        //关闭线程池
        executorService.shutdown();
        log.info("count:{}", count);

    }

    public static void add() {
        long stamp = stampedLock.writeLock();
        try {
            count++;
        } finally {
            //释放时需传入加锁时获取的stamp
            stampedLock.unlock(stamp);
        }
    }


}
