package com.chansan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chansan.core.Vps;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "this springboot 3.0 is build with gradle  and base on jdk17";
    }


    @GetMapping("/query")
    public Object query(){
        List<Vps> vps = new Vps().selectAll();
        return vps;
    }
}
