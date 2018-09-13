package com.imooc.concurrency.Example.singleton;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 19:11
 **/

import com.imooc.concurrency.annotation.ThreadSafe;

/**
 * 饿汉式(静态代码块初始化)
 * 1、除了使用静态域直接初始化单例对象，还可以用静态块初始化单例对象。
 * 2、值得注意的一点是，静态域与静态块的顺序一定不要反，在写静态域和静态方法的时候，一定要注意顺序，
 *    不同的静态代码块是按照顺序执行的，它跟我们正常定义的静态方法和普通方法是不一样的。
 */
@ThreadSafe
public class SingletonExample6 {

    //私有构造
    private SingletonExample6(){
    }

    //单例实例
    private static SingletonExample6 instance = null;

    static {
        instance = new SingletonExample6();
    }

    //静态工厂方法
    public SingletonExample6 getInstance(){
        return instance;
    }

}
