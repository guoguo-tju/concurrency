package com.imooc.concurrency.Example.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @program: concurrency
 * @description:
 *   CountDownLatch，通过给定的计数器数值为其初始化，该计数器是原子性操作，保证同时只有一个线程去操作该计数器。
 *   调用该类await方法的线程会一直处于阻塞状态。只有其他线程调用countDown方法（每次使计数器-1）,使计数器归零才能继续执行。
 *
 * @author: Karl Guo
 * @create: 2018-10-09 17:39
 **/
public class CountDownLatchExample {

    private static CountDownLatch countDownLatch = new CountDownLatch(12);



}
