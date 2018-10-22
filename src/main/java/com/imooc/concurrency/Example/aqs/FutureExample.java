package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @program: concurrency
 * @description: 实现Futrue或者Callable接口是可以获取到线程执行完的返回值的
 * <p>
 * Future接口提供了一系列方法用于控制线程执行计算:
 * boolean cancel(boolean mayInterruptIfRunning);//取消任务
 * boolean isCancelled();//是否被取消
 * boolean isDone();//计算是否完成
 * V get() throws InterruptedException, ExecutionException;//获取计算结果，在执行过程中任务被阻塞
 * V get(long timeout, TimeUnit unit)//timeout等待时间、unit时间单位 throws InterruptedException, ExecutionException, TimeoutException;
 * @author: Karl Guo
 * @create: 2018-10-22 19:44
 **/
@Slf4j
public class FutureExample {

    static class Mycallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "done";
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //executorService.sumbit(Callable callable),是有返回值的,返回值是future
        Future<String> future = executorService.submit(new Mycallable());
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();  //一直阻塞,直到call()执行完
        log.info("result : {}", result);

    }


}
