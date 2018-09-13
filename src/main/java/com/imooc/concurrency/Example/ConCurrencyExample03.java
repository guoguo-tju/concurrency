package com.imooc.concurrency.Example;

import com.imooc.concurrency.annotation.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
@ThreadSafe
public class ConCurrencyExample03 {

    /**
     * AtomicIntegerFieldUpdater,原子性地去更新某一个类的实例中的某一字段
     * newUpdater()方法的第一个参数是某类的class文件,第二个参数是指定的字段名
     * 注意:该字段必须是volatile修饰,并且不能是static修饰.
     */
    public static AtomicIntegerFieldUpdater<ConCurrencyExample03> updater = AtomicIntegerFieldUpdater.newUpdater(ConCurrencyExample03.class,"count");


    //该字段必须是volatile修饰,并且不能是static修饰.
   @Getter
   public volatile int count =100 ;

   public static void main(String[] args){
       ConCurrencyExample03 conCurrencyExample03 = new ConCurrencyExample03();

       if (updater.compareAndSet(conCurrencyExample03,100,200)){
            log.info("update success 1 , count = {}" , conCurrencyExample03.getCount());
       }

       if (updater.compareAndSet(conCurrencyExample03,100,300)){
           log.info("update success 2 , count = {}",conCurrencyExample03.getCount());
       }else{
           log.info("update fail , count = {}", conCurrencyExample03.getCount());
       }


   }

}
