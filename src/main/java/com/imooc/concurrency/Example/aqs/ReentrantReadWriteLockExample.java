package com.imooc.concurrency.Example.aqs;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: concurrency
 * @description:
 *
 * 写锁只有在读锁没有被获取的情况下才能获取.
 * 如果一直存在读操作，那么写锁一直在等待没有读的情况出现，这样我的写锁就永远也获取不到，就会造成等待获取写锁的线程饥饿。
 *
 * @author: Karl Guo
 * @create: 2018-10-18 14:40
 **/
public class ReentrantReadWriteLockExample {

    private static Map<String , Data> map = new TreeMap<>();

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static Lock readLock = reentrantReadWriteLock.readLock();

    private static Lock writeLock = reentrantReadWriteLock.writeLock();

    //加读锁
    public Data get(String key){
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    //加写锁
    public Data put(String key , Data data){
        writeLock.lock();
        try {
            return map.put(key , data);
        } finally {
            writeLock.unlock();
        }
    }

    //内部类Data
    class Data{

    }


}
