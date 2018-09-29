package com.imooc.concurrency.Example.threadLocal;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-29 14:08
 **/
public class ThreadLocalCache {

    private final static ThreadLocal<String> threadLocalCache = new ThreadLocal<>();

    /**
     * 将请求路径添加到当前线程对象中
     */
    public static void add(String id){
        threadLocalCache.set(id);
    }

    /**
     * 获取当前线程对象中的请求路径
     * @return
     */
    public static String getString(){
        return threadLocalCache.get();
    }

    /**
     * 移除当前线程内的值
     */
    public static void remove(){
        threadLocalCache.remove();
    }


}
