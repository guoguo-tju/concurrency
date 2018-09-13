package com.imooc.concurrency.Example.singleton;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 17:23
 **/

import com.imooc.concurrency.annotation.ThreadSafe;
import com.imooc.concurrency.annotation.UnThreadSafe;

/**
 * 懒汉式(双重同步锁单例模式)
 *   我们将上面的第二个例子(懒汉式（synchronized))进行了改进，由synchronized修饰方法改为在方法内部锁定，
 *   再加上双重的检测机制，保证了最大程度上的避免耗损性能,因为一旦初始化成功，再也走不到锁等待的代码。
 *
 * 线程不安全:
 *      这里有一个知识点：CPU指令相关
 *      在上述代码中，执行new操作的时候，CPU一共进行了三次指令
 *      （1）memory = allocate() 分配对象的内存空间
 *      （2）ctorInstance() 初始化对象
 *      （3）instance = memory 设置instance指向刚分配的内存
 *      在程序运行过程中，CPU为提高运算速度会做出违背代码原有顺序的优化。我们称之为乱序执行优化或者说是指令重排。
 *
 *      那么上面知识点中的三步指令极有可能被优化为（1）（3）（2）的顺序。
 *      当我们有两个线程A与B，A线程遵从132的顺序，经过了两此instance的空值判断后，
 *      执行了new操作，并且cpu在某一瞬间刚结束指令（3），并且还没有执行指令（2）。
 *      而在此时线程B恰巧在进行第一次的instance空值判断，由于线程A执行完（3）指令，
 *      为instance分配了内存，线程B判断instance不为空，直接执行return，返回了instance，这样就出现了错误。
 *
 * 解决方法:
 *      在对象声明时使用volatile关键字修饰，阻止CPU的指令重排。
 *      private volatile static SingletonExample instance = null;
 */
@UnThreadSafe
public class SingletonExample3 {

    //私有的构造方法
    private SingletonExample3(){
    }

    //单例对象
    private static SingletonExample3 instance = null;

    //静态工厂方法
    public static  SingletonExample3 getInstance(){
        if (instance == null){    //双重检测机制
            synchronized (SingletonExample3.class){    //同步锁
                if (instance == null){
                    instance =  new SingletonExample3();
                }
            }
        }
        return instance;
    }


}
