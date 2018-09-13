package com.imooc.concurrency.Example.publish;

import com.imooc.concurrency.annotation.UnThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 16:16
 **/

@Slf4j
@UnThreadSafe
public class Escape {

    /**
     * 在对象未构造完成之前将其发布
     * <p>
     * 上述代码在函数构造过程中启动了一个线程。无论是隐式的启动还是显式的启动，都会造成这个this引用的溢出。新线程总会在所属对象构造完毕之前就已经看到它了。
     * 因此要在构造函数中创建线程，那么不要启动它，而是应该采用一个专有的start或者初始化的方法统一启动线程
     * 这里其实我们可以采用工厂方法和私有构造函数来完成对象创建和监听器的注册等等，这样才可以避免错误
     */
    private int canBeEscape;

    public Escape() {
        new InnerClass();
    }

    private class InnerClass {
        public InnerClass() {
            log.info("{}", Escape.this.canBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }

}
