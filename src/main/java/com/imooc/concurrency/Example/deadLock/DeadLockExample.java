package com.imooc.concurrency.Example.deadLock;

import lombok.extern.slf4j.Slf4j;


/**
 *  死锁的demo
 *
 *  定位死锁方法:
 *  选取最常见的 jstack
 *  首先，可以使用 jps 或者系统的 ps 命令、任务管理器等工具，确定进程 ID。
 *  其次，调用 jstack 获取线程栈：
 *  ${JAVA_HOME}\bin\jstack your_pid
 *  然后，分析得到的输出:
 *  ...
 *  结合代码分析线程栈信息。上面这个输出非常明显，找到处于 BLOCKED 状态的线程，按照试图获取（waiting）的锁 ID查找，很快就定位问题。
 *  jstack 本身也会把类似的简单死锁抽取出来，直接打印出来。
 */
@Slf4j
public class DeadLockExample implements Runnable {
    public int flag = 1;
    //静态对象是类的所有对象共享的
    private static Object o1 = new Object(), o2 = new Object();


    @Override
    public void run() {
        log.info("flag:{}", flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    log.info("1");
                }
            }
        }
        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    log.info("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLockExample td1 = new DeadLockExample();
        DeadLockExample td2 = new DeadLockExample();
        td1.flag = 1;
        td2.flag = 0;
        //td1,td2都处于可执行状态，但JVM线程调度先执行哪个线程是不确定的。
        //td2的run()可能在td1的run()之前运行
        new Thread(td1).start();
        new Thread(td2).start();
        /**
         DeadLock类的对象flag==1时（td1），先锁定o1,睡眠500毫秒
         而td1在睡眠的时候另一个flag==0的对象（td2）线程启动，先锁定o2,睡眠500毫秒
         td1睡眠结束后需要锁定o2才能继续执行，而此时o2已被td2锁定；
         td2睡眠结束后需要锁定o1才能继续执行，而此时o1已被td1锁定；
         td1、td2相互等待，都需要得到对方锁定的资源才能继续执行，从而死锁。
         */
    }
}
