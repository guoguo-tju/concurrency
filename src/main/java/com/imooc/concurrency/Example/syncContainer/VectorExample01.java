package com.imooc.concurrency.Example.syncContainer;

import com.imooc.concurrency.annotation.UnThreadSafe;

import java.util.Vector;

/**
 * Vector相当于加了synchronize的arrayList
 * Vector在某些情况系不是完全线程安全的: 同时并发调用两个方法时
 * 运行结果：报错java.lang.ArrayIndexOutOfBoundsException: Array index out of range
 *   原因分析：同时发生获取与删除的操作。当两个线程在同一时间都判断了vector的size，假设都判断为9，
 *   而下一刻线程1执行了remove操作，随后线程2才去get，所以就出现了错误。synchronized关键字可以保证同一时间
 *   只有一个线程执行该方法，但是多个线程同时分别执行remove、add、get操作的时候就无法控制了。
 *   需要手动加上Lock或者synchronized来同步.
 */
@UnThreadSafe
public class VectorExample01 {

    private static Vector vector = new Vector();

    public static void main(String[] args) {
        while (true){
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread thread01 = new Thread(){
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            };

            Thread thread02 = new Thread(){
                public void run(){
                    for (int i = 0; i < vector.size(); i++) {
                        vector.get(i);
                    }
                }
            };
            thread01.start();
            thread02.start();
        }


    }


}
