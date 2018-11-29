package com.imooc.concurrency.Example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: concurrency
 * @description:
 *
 *      Hystrix框架的服务降级功能
 *
 *      监控页面: localhost:8080/hystrix  (项目名:端口号/hystrix)
 *      Delay: 20ms (延时多长时间刷新一次)    Title: 名字随便写
 *      调用过的请求接口都可以监控
 *
 * @author: Karl Guo
 * @create: 2018-11-29 19:43
 **/
@Slf4j
@Controller
@RequestMapping("/hystrix1")
@DefaultProperties(defaultFallback = "defaultFail")
public class HystrixController1 {

    @HystrixCommand(fallbackMethod = "fail1")   //该注解表示:如果test1方法出现异常,则服务降级,调用fail1的方法
    @RequestMapping("/test1")
    @ResponseBody
    public String test1() {
        throw new RuntimeException();
    }

    private String fail1() {
        log.warn("fail1");
        return "fail1";
    }

    @HystrixCommand(fallbackMethod = "fail2")
    @RequestMapping("/test2")
    @ResponseBody
    public String test2() {
        throw new RuntimeException();
    }

    @HystrixCommand(fallbackMethod = "fail3")
    private String fail2() {
        log.warn("fail2");
        throw new RuntimeException();
    }

    @HystrixCommand            //没有指定哪个方法做服务降级,就去@DefaultProperties(defaultFallback = "defaultFail")找defaultFail方法
    private String fail3() {
        log.warn("fail3");
        throw new RuntimeException();
    }

    private String defaultFail() {
        log.warn("default fail");
        return "default fail";
    }}
