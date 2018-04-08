package com.ximo.springbootblogmaster.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description hello控制器.
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World! Welcome to visit here!";
    }

}
 