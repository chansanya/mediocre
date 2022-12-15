package com.chansan.domain.system.service.impl;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.chansan.common.exception.ServiceException;

import cn.hutool.core.lang.Snowflake;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

;

/**
 * 登录校验方法
 *
 * @author yf
 */
@Slf4j
@Service
public class SysLoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    public String login(String username, String password) {
        return authentication(new UsernamePasswordAuthenticationToken(username, password));
    }


    /**
     * 登录验证
     *
     * @return 结果
     */
    public String authentication(Authentication authentication) {

        try {

            Authentication authenticate = authenticationManager.authenticate(authentication);
            log.info("authentication:{}",authenticate.getPrincipal());
            // 生成token
            return new Snowflake().nextIdStr();
        } catch (Exception e) {
            log.error("登录异常",e);
            if (e instanceof BadCredentialsException) {
                throw new ServiceException("用户名密码不匹配");
            } else {
                throw new ServiceException(e.getMessage());
            }
        }

    }



}
