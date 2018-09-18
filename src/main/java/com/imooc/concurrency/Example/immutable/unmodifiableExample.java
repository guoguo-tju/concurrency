package com.imooc.concurrency.Example.immutable;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-18 14:23
 **/

/**
 * Java的Collection类的unmodifiable相关方法，可以创建不可变对象
 * 抛出异常UnsupportedOperationException
 */
@Slf4j
public class unmodifiableExample {

    private static  Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        log.info("{}",map.get(1));
    }


}
