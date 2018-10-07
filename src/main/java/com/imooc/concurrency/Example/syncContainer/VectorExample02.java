package com.imooc.concurrency.Example.syncContainer;


import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Vector;

/**
 *使用foreach\iterator遍历集合的时候不能进行增删操作,会报错ConcurrentModificationException,使用for循环是ok的.
    解决办法:1.在循环里将循坏的对象标记,然后出循环时处理
             2.在并发环境在可以通过加锁,或者使用并发容器来解决
 */
@Slf4j
public class VectorExample02 {

    public static Vector<Integer> vector = new Vector<Integer>();

    public static void test01(){
        for (Integer v : vector) {
            if (v.equals(3)){
                vector.remove(v);
            }
        }
    }

    public static void test02(){
        Iterator<Integer> iterator = vector.iterator();
        while (iterator.hasNext()){
            Integer v = iterator.next();
            if (v.equals(3)){
                vector.remove(v);
            }
        }
    }

    public static void test03(){
        for (int i = 0 ; i < vector.size() ; i++){
            Integer v = vector.get(i);
            if (v.equals(3)){
                vector.remove(v);
            }
        }

    }

    public static void main(String[] args) {
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test03();

    }


}
