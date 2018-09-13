package com.imooc.concurrency.Example.singleton;

import com.google.common.collect.Maps;
import com.imooc.concurrency.annotation.ReCommend;
import com.imooc.concurrency.annotation.ThreadSafe;

import java.util.Map;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 19:20
 **/

/**
 * (枚举式,推荐)
 * 由于枚举类的特殊性，枚举类的构造函数Singleton方法只会被实例化一次，且是这个类被调用之前。这个是JVM保证的。
 * 相比于懒汉式,安全性更保证
 * 相比于饿汉式,类实例是在调用时才初始化的,不会浪费资源
 */
@ThreadSafe
@ReCommend
public class SingletonExample7 {

    private final static Map<Integer,Integer> map = Maps.newHashMap();

    private SingletonExample7(){

    }

    public SingletonExample7 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton{
        INSTANCE;

        private SingletonExample7 singleton;
        //JVM保证这个方法只调用一次
        Singleton(){
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getInstance(){
            return singleton;
        }
    }


}
