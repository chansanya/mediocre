package com.chansan.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chansan.common.base.resp.AjaxResult;
import com.chansan.core.security.authentication.CaptchaAuthenticationProvider;
import com.chansan.core.security.userdetails.CaptchaDetailsServiceImpl;
import com.chansan.modules.sms.ISmsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @name: AutnConfiguration
 * @author: leihuangyan
 * @classPath: com.chansan.core.security.ProviderConfiguration
 * @date: 2022/12/15
 * @description:
 */
@Slf4j
@Configuration
public class ProviderConfiguration {


    @Bean
    public ISmsService smsService() {
        return new ISmsService() {
            @Override
            public AjaxResult sendCheckCode(String phoneNumbers) {
                return AjaxResult.success();
            }

            @Override
            public AjaxResult sendMsg(String content, String... phoneNumbers) {
                return AjaxResult.success();
            }

            @Override
            public boolean verifyCaptcha(String phone, String code) {
                return Boolean.TRUE;
            }
        };
    }




    @Bean
    public CaptchaAuthenticationProvider captchaAuthenticationProvider(ISmsService smsService, CaptchaDetailsServiceImpl userDetailsService) {
        return new CaptchaAuthenticationProvider(userDetailsService, smsService);
    }

//    @Bean
//    public UserPassAuthenticationProvider userPassAuthenticationProvider(UserPwdDetailsServiceImpl userDetailsServiceImpl) {
//        return new UserPassAuthenticationProvider( userDetailsServiceImpl);
//    }


}
