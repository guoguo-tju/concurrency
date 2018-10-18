package com.imooc.concurrency.Example.singleton;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 17:23
 **/

import com.imooc.concurrency.annotation.UnThreadSafe;

/**
 * 单例: 一个类只有一个对象实例
 * 懒汉式(最简式) : 在调用时才第一次创建实例
 * 线程不安全:
 *   在多线程环境下，当两个线程同时访问这个方法，同时制定到instance==null的判断。都判断为null，接下来同时执行new操作。这样类的构造函数被执行了两次。
 */
@UnThreadSafe
public class SingletonExample1 {

    //1.私有的构造方法
    private SingletonExample1(){
    }

    //2.单例对象
    private static SingletonExample1 instance = null;

    //3.静态工厂方法
    public static SingletonExample1 getInstance(){
        if (instance == null){
            instance =  new SingletonExample1();
        }
        return instance;
    }


}
