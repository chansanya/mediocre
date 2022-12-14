package com.chansan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chansan.common.base.resp.AjaxResult;
import com.chansan.common.exception.DemoModeException;
import com.chansan.common.exception.ServiceException;
import com.chansan.common.exception.UtilException;
import com.chansan.common.exception.WebSocketException;
import com.chansan.core.annotations.IgnoreGlobalResp;
import com.chansan.modules.vps.domain.Vps;

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


    @GetMapping("/cmd/{cmd}")
    Object danger(@PathVariable("cmd") Integer cmd){

        var result=  switch (cmd){
            case 1 -> throw new WebSocketException("1号异常");
            case 2 -> throw new ServiceException("2号异常");
            case 3 -> throw new UtilException("3号异常");
            case 4 -> throw new DemoModeException();
            case 5 ->  5/0;
            default -> "正常";
        };
        return AjaxResult.success(result);
    }

}
