package com.imooc.concurrency.Example.threadLocal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: concurrency
 * @description:
 * @author: Karl Guo
 * @create: 2018-09-29 15:27
 **/

@Controller
@RequestMapping("/threadLocal")
public class ThreadLocalController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return RequestHolder.getString();
    }


}
