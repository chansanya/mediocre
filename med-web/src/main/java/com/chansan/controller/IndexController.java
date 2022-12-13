package com.chansan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name: IndexController
 * @author£º yf
 * @classPath: com.chansan.controller.IndexController
 * @date: 2022/12/13
 * @Version: 1.0
 * @description: ÎÄ¼þÃèÊö:
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "this springboot 3.0 is build with gradle  and base on jdk17";
    }

}
