package com.imooc.concurrency.Example.threadLocal;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @program: concurrency
 * @description: 过滤器获取请求路径放在当前线程里
 * 如果是做登录filter:
 * 将userId放在浏览器的cookie里
 * 获取httpRequest中的cookies[],从数组中取cookie.getName为"userId"的value值userId,用userId从数据库中查user
 * 如果user为null,说明"用户未登录或登录失效"
 * 如果user不为null,将user值放进当前线程里ThreadLocalCache.addUser(user)
 * 后续如果需要用user直接从当前线程里取即可.
 * @author: Karl Guo
 * @create: 2018-09-29 14:15
 **/
@Slf4j
public class HttpFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //打印当前线程id与请求路径
        log.info("into httpFilter , threadLocal id is {} , url is {}", Thread.currentThread().getId() , request.getServletPath());
        //将请求路径放在当前线程对象threadLocal里
        RequestHolder.add(request.getServletPath());
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
