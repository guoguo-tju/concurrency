package com.imooc.concurrency.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义的注解,表明该类是线程安全的
 */
@Target(ElementType.TYPE)     //表明该注解作用在类上
@Retention(RetentionPolicy.SOURCE)      //表明该注解的生命周期,在编译时就不用被包含
public @interface ThreadSafe {

    String value() default "";

}
