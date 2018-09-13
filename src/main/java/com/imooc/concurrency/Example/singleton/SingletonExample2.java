package com.imooc.concurrency.Example.singleton;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 17:23
 **/

import com.imooc.concurrency.annotation.ThreadSafe;
import com.imooc.concurrency.annotation.UnReCommend;
import com.imooc.concurrency.annotation.UnThreadSafe;

/**
 * 懒汉式(在静态工厂方法上加synchronize)
 * 线程安全:
 *   1、使用synchronized修饰静态方法后，保证了方法的线程安全性，同一时间只有一个线程访问该方法
 *   2、有缺陷：会造成性能损耗
 */
@ThreadSafe
@UnReCommend
public class SingletonExample2 {

    //私有的构造方法
    private SingletonExample2(){

    }

    //单例对象
    private static SingletonExample2 instance = null;

    //静态工厂方法
    public static synchronized SingletonExample2 getInstance(){
        if (instance == null){
            instance =  new SingletonExample2();
        }
        return instance;
    }


}
