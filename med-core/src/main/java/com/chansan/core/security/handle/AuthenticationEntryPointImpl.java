package com.chansan.core.security.handle;

import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.chansan.common.utils.JacksonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


/**
 * 认证失败处理类 返回未授权
 * 
 * @author yf
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        var msg = String.format("请求访问：[%s]，认证失败，无法访问系统资源", request.getRequestURI());
        log.warn(msg);
        JacksonUtil.writeValue(response,msg);
    }
}
