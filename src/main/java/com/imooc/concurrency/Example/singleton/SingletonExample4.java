package com.imooc.concurrency.Example.singleton;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 17:23
 **/

import com.imooc.concurrency.annotation.ThreadSafe;

/**
 * 懒汉式(双重同步锁单例模式)
 *
 * 线程安全:
 *      在对象声明时使用volatile关键字修饰，阻止CPU的指令重排。
 *      private volatile static SingletonExample instance = null;
 */
@ThreadSafe
public class SingletonExample4 {

    //私有的构造方法
    private SingletonExample4(){
    }

    //单例对象
    private volatile static SingletonExample4 instance = null;

    //静态工厂方法
    public static synchronized SingletonExample4 getInstance(){
        if (instance == null){    //双重检测机制
            synchronized (SingletonExample4.class){    //同步锁
                if (instance == null){
                    instance =  new SingletonExample4();
                }
            }
        }
        return instance;
    }


}
