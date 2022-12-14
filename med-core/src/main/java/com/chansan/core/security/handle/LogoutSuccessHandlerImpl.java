package com.chansan.core.security.handle;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.chansan.common.exception.ServiceException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * 自定义退出处理类 返回成功
 *
 * @author yf
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    /**
     * 退出处理
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        throw new ServiceException(String.format("退出系统:[%s]", request.getRequestURI()));
    }
}
