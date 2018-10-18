package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: concurrency
 * @description: ReentrantLock与synchronized的区别
 * 可重入性：两者的锁都是可重入的，差别不大，有线程进入锁，计数器自增1，等下降为0时才可以释放锁
 * 锁的实现：synchronized是基于JVM实现的（用户很难见到，无法了解其实现），ReentrantLock是JDK实现的。
 * 性能区别：在最初的时候，二者的性能差别差很多，当synchronized引入了偏向锁、轻量级锁（自选锁）后，二者的性能差别不大，官方推荐synchronized（写法更容易、在优化时其实是借用了ReentrantLock的CAS技术，试图在用户态就把问题解决，避免进入内核态造成线程阻塞）
 * 功能区别：
 * （1）便利性：synchronized更便利，它是由编译器保证加锁与释放。ReentrantLock是需要手动释放锁，所以为了避免忘记手工释放锁造成死锁，所以最好在finally中声明释放锁。
 * （2）锁的细粒度和灵活度，ReentrantLock优于synchronized
 * <p>
 * ReentrantLock独有的功能
 * 可以指定是公平锁还是非公平锁，sync只能是非公平锁。（所谓公平锁就是先等待的线程先获得锁）
 * 提供了一个Condition类，可以分组唤醒需要唤醒的线程。不像是synchronized要么随机唤醒一个线程，要么全部唤醒。
 * 提供能够中断等待锁的线程的机制，通过lock.lockInterruptibly()实现，这种机制 ReentrantLock是一种自选锁，通过循环调用CAS操作来实现加锁。性能比较好的原因是避免了进入内核态的阻塞状态。
 * @author: Karl Guo
 * @create: 2018-10-18 11:49
 **/
@Slf4j
public class ReentrantLockExample {

    //线程总访问量
    public static final Integer clientTotal = 5000;
    //线程的并发数
    public static final Integer threadTotal = 20;

    private static Integer count = 0;

    //在new ReentrantLock的时候默认给了一个不公平锁 ,
    //也可以加参数来初始化指定使用公平锁还是不公平锁, public ReentrantLock(boolean fair)
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        Semaphore semaphore = new Semaphore(threadTotal);

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < clientTotal; i++) {
            final int treadNum = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add(treadNum);
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    log.error("Exception", e);
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count = {}",count);
    }

    private static void add(int treadNum) {
        reentrantLock.lock();
        count++;
        reentrantLock.unlock();
    }

}
