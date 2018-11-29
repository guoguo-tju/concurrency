package com.imooc.concurrency.Example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
 * @author: Karl Guo
 * @create: 2018-11-29 19:43
 **/

@Slf4j
@Controller
@RequestMapping("/hystrix2")
@DefaultProperties(defaultFallback = "defaultFail")
public class HystrixController2 {

    @HystrixCommand(commandProperties = {
       @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")   //这个接口的处理时间超过500s,就会降级处理
    })
    @RequestMapping("/test1")
    @ResponseBody
    public String test1() throws Exception {
        Thread.sleep(1000);
        return "test1";
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")},
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),             //支持线程池相关的参数
                    @HystrixProperty(name = "maxQueueSize", value = "101"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            })
    @RequestMapping("/test2")
    @ResponseBody
    public String test2() throws Exception {
        Thread.sleep(1000);
        return "test2";
    }

    private String defaultFail() {
        log.warn("default fail");
        return "default fail";
    }
}