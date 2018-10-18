package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: concurrency
 * @description:
 *
 * Condition可以非常灵活的操作线程的唤醒，下面是一个线程等待与唤醒的例子，其中用1234序号标出了日志输出顺序:
 *  过程详解:
 * 1、线程1调用了reentrantLock.lock()，线程进入AQS等待队列，输出1号log
 * 2、接着调用了awiat方法，线程从AQS队列中移除，锁释放，直接加入condition的等待队列中
 * 3、线程2因为线程1释放了锁，拿到了锁，输出2号log
 * 4、线程2执行condition.signalAll()发送信号，输出3号log
 * 5、condition队列中线程1的节点接收到信号，从condition队列中拿出来放入到了AQS的等待队列,这时线程1并没有被唤醒。
 * 6、线程2调用unlock释放锁，因为AQS队列中只有线程1，因此AQS释放锁按照从头到尾的顺序，唤醒线程1
 * 7、线程1继续执行，输出4号log，并进行unlock操作。
 *
 * @author: Karl Guo
 * @create: 2018-10-18 14:08
 **/
@Slf4j
public class ConditionExample {

    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition(); //创建condition

        // 线程1
        new Thread(() -> {
            try {
                reentrantLock.lock();
                log.info("wait signal"); // 1
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("get signal");  // 4
            reentrantLock.unlock();
        }).start();

        //线程2
        new Thread(() -> {
            reentrantLock.lock();
            log.info("get lock"); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();//发送信号
            log.info("send signal"); // 3
            reentrantLock.unlock();
        }).start();
    }


}
