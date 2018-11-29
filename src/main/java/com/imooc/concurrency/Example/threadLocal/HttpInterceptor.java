package com.imooc.concurrency.Example.threadLocal;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: concurrency
 * @description:
 *  重写preHandle()(它是进入url之前拦截处理)和afterCompletion()(它是执行完url之后拦截处理)方法,在afterCompletion方法中ThreadLocalCache.remove()释放,避免内存泄漏.
 * @author: Karl Guo
 * @create: 2018-09-29 15:02
 **/

@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandle....");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //方法结束后删除当前线程中的value
        RequestHolder.remove();
        log.info("afterCompletion....");
        return;
    }
}
