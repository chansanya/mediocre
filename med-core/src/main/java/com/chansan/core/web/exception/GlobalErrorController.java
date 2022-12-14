package com.chansan.core.web.exception;


import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.chansan.common.base.resp.AjaxResult;
import com.chansan.common.base.resp.RespStatus;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @name: GlobalErrorController
 * @author: leihuangyan
 * @classPath: com.chansan.core.web.exception.GlobalErrorController
 * @date: 2022/12/14
 * @description: 全局错误
 */
@Slf4j
@RestController
public class GlobalErrorController extends AbstractErrorController {

    public static final String ERROR_PATH = "/error";

    @Resource
    private ErrorAttributes errorAttributes;

    public GlobalErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }



    @RequestMapping(value = ERROR_PATH)
    public AjaxResult error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        var httpStatus = HttpStatus.valueOf(response.getStatus());
        log.error("服务异常:{}",httpStatus.value());

        var  error = errorAttributes.getError(new ServletWebRequest(request));
        if(null != error){
            log.error("服务异常>>>:{}",error.getMessage());
        }

        //响应码
        response.setStatus(httpStatus.value());

        return switch (httpStatus) {
            case NOT_FOUND -> AjaxResult.error(httpStatus.value(), "请求地址不存在");
            case METHOD_NOT_ALLOWED -> AjaxResult.error(httpStatus.value(), "不支持请求方式");
            default -> AjaxResult.error(RespStatus.ERROR, "服务异常,请联系管理员");
        };

    }


}

