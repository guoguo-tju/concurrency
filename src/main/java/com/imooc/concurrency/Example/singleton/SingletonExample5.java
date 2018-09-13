package com.imooc.concurrency.Example.singleton;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 19:11
 **/

import com.imooc.concurrency.annotation.ThreadSafe;

/**
 * 饿汉式(最简式)
 * 1、饿汉模式由于单例实例是在类装载的时候进行创建，因此只会被执行一次，所以它是线程安全的。
 * 2、该方法存在缺陷：如果构造函数中有着大量的事情操作要做，那么类的装载时间会很长，影响性能。如果只是做的类的构造，却没有引用，那么会造成资源浪费
 * 3、饿汉模式适用场景为：（1）私有构造函数在实现的时候没有太多的处理（2）这个类在实例化后肯定会被使用
 */
@ThreadSafe
public class SingletonExample5 {

    //私有构造
    private SingletonExample5(){
    }

    //单例实例(只在类初始化的时候创建一次,线程安全)
    private static SingletonExample5 instance = new SingletonExample5();

    //静态工厂方法
    public SingletonExample5 getInstance(){
        return instance;
    }

}
