package com.imooc.concurrency.Example.aqs;

import lombok.extern.slf4j.Slf4j;
import sun.net.www.protocol.file.FileURLConnection;

import java.util.concurrent.*;

/**
 * @program: concurrency
 * @description:
 *
 *  Future实现了RunnableFuture接口，而RunnableFuture接口继承了Runnable与Future接口
 *  所以它既可以作为Runnable被线程中执行，又可以作为callable获得返回值。
 *
 *  FutureTask支持两种参数类型，Callable和Runnable，在使用Runnable 时，还可以多指定一个返回结果类型。
 *
 *  public FutureTask(Callable<V> callable) {
 *      if (callable == null)
 *          throw new NullPointerException();
 *      this.callable = callable;
 *      this.state = NEW;     // ensure visibility of callable
 *      }
 *
 *      public FutureTask(Runnable runnable, V result) {
 *      this.callable = Executors.callable(runnable, result);
 *      this.state = NEW;     // ensure visibility of callable
 *      }
 *
 * @author: Karl Guo
 * @create: 2018-10-23 10:37
 **/
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //<V>V表示返回结果的类型
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });

//        ExecutorService executorService = Executors.newCachedThreadPool();
//        Future<?> future = executorService.submit(futureTask);
//        Object o = future.get();

        new Thread(futureTask).start();
        log.info("do something in main");
        Thread.sleep(1000);
        String result = futureTask.get();
        log.info("result = {}" , result);
    }


}
