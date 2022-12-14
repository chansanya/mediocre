package com.chansan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chansan.core.Vps;
import com.chansan.core.handler.IgnoreGlobalResp;

/**
 * @author yf
 */
@RestController
public class IndexController {

    @GetMapping("/")
    @IgnoreGlobalResp
    public String index(){
        return "this springboot 3.0 is build with gradle  and base on jdk17";
    }


    @GetMapping("/query")
    public Object query(){
        return  new Vps().selectAll();
    }


}
