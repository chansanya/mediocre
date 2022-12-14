package com.chansan.core.web.handler;

import java.lang.reflect.Method;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.chansan.common.base.resp.AjaxResult;
import com.chansan.core.annotations.IgnoreGlobalResp;

import lombok.extern.slf4j.Slf4j;

/**
 * @name: GlobalResponseBodyHandler
 * @author： LHY
 * @classPath: com.rourou.framework.handler.GlobalResponseBodyHandler
 * @date: 2022/5/19 18:07
 * @description: GlobalResponseBodyHandler
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.chansan")
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = returnType.getMethod();
        if(null == method){
            return false;
        }

        IgnoreGlobalResp ignoreGlobalResp = method.getAnnotation(IgnoreGlobalResp.class);

        if(null != ignoreGlobalResp && ignoreGlobalResp.ignore()){
            log.debug("当前为忽略处理接口");
            return false;
        }

        log.debug("当前返回数据:{}",returnType.getParameterType().getName());
        return !returnType.getParameterType().getName().equals(AjaxResult.class.getName());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  @NotNull MediaType selectedContentType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NotNull ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response
    ) {

        Class<?> clazz = returnType.getParameterType();

        log.debug("当前返回类型:{}",clazz);
        Method method = returnType.getMethod();
        assert method != null;

        log.debug("当前返回method:{}",method);
        return AjaxResult.success(body);
    }
}
