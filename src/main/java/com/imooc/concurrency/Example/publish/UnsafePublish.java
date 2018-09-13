package com.imooc.concurrency.Example.publish;

import com.imooc.concurrency.annotation.UnThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-11 16:01
 **/

@Slf4j
@UnThreadSafe
public class UnsafePublish {

    /**
     * 不安全发布对象
     *
     * 任何一个线程都可以通过公有的方法getStates()访问/修改私有的属性states
     * 当我在任何一个线程真的想使用states数据时,他数据是不确定的,所以这个类是线程不安全的
     */
    private String[] states = { "a" , "b" , "c"};

    public String[] getStates(){
        return states;
    }

    public static void main(String[] args){
        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("{}", Arrays.toString(unsafePublish.getStates()));
        unsafePublish.getStates()[0] = "d";
        log.info("{}",Arrays.toString(unsafePublish.getStates()));
    }
}
