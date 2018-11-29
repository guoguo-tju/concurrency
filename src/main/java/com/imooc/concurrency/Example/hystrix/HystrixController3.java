package com.imooc.concurrency.Example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @program: concurrency
 * @description:
 *
 *      Hystrix框架的服务熔断
 *      (过载保护: 错误率超过设置的熔断比例后服务熔断,再次恢复正常期间即使能正常处理的请求也会被降级)
 *
 * @author: Karl Guo
 * @create: 2018-11-29 19:46
 **/

@Slf4j
@Controller
@RequestMapping("/hystrix3")
@DefaultProperties(defaultFallback = "defaultFail")
public class HystrixController3 {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //  熔断器在整个统计时间内是否开启
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),  // 至少有3个请求才进行熔断错误比率计算
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"), //当出错率超过50%后熔断器启动
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),  // 熔断器工作时间，超过这个时间，先放一个请求进去，成功的话就关闭熔断，失败就再等一段时间
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")// 统计滚动的时间窗口
    })
    @RequestMapping("/test1")
    @ResponseBody
    public String test1(@RequestParam("id") Integer id) {
        log.info("id:{}", id);
        if (id % 2 == 0) {
            throw new RuntimeException();     //是偶数的话抛出异常
        }
        return "test_" + id;
    }

    private String defaultFail() {
        log.warn("default fail");
        return "default fail";
    }
}