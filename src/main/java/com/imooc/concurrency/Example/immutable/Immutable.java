package com.imooc.concurrency.Example.immutable;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-18 14:39
 **/

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;


/**
 * 使用Guava的Immutable相关类也可以创建不可变对象
 * 我们在使用一个不可变对象的实例时，其实很关键一点就是添加final修饰，
 * 否则我们很多接口来声明的类实例，在实际使用中可能被改了引用。
 */
public class Immutable {

    //1.ImmutableList.of(...)
    private static final ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);

    public static void main(String[] args) {
        list.add(4);
    }

    //2.ImmutableSet.copyOf(list)
    //ImmutableSet除了使用of的方法进行初始化，还可以使用copyof方法，将Collection类型、Iterator类型作为参数。
    private static final ImmutableSet set = ImmutableSet.copyOf(list);
    private static final ImmutableSet set2 = ImmutableSet.copyOf(list.iterator());


    private static final ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder().put(1,2).put(3,4).put(5,6).build();
    //3.ImmutableMap的builder写法
    private static final ImmutableMap<Integer , Integer> map = ImmutableMap.of(1,2,3,4);  //奇数位是key,偶数位是value


}
