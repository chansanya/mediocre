package com.chansan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "this springboot 3.0 is build with gradle  and base on jdk17";
    }

}
