package com.chansan.core.security;


import java.util.HashSet;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.chansan.common.base.resp.AjaxResult;
import com.chansan.core.security.authentication.CaptchaAuthenticationProvider;
import com.chansan.core.security.filter.JwtAuthenticationTokenFilter;
import com.chansan.core.security.handle.AuthenticationEntryPointImpl;
import com.chansan.core.security.handle.LogoutSuccessHandlerImpl;
import com.chansan.modules.sms.ISmsService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * spring security配置
 *
 * @author rourou
 */
@Slf4j
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {


    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }


    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .requestMatchers("/login", "/register", "/captchaImage", "/captcha/login", "/sms/send").anonymous()
                .requestMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js",
                        "/profile/**"
                ).permitAll()
//                .requestMatchers("/query").anonymous()
                .requestMatchers("/swagger-ui.html").anonymous()
                .requestMatchers("/swagger-resources/**").anonymous()
                .requestMatchers("/webjars/**").anonymous()
                .requestMatchers("/*/api-docs").anonymous()
                .requestMatchers("/druid/**").anonymous()
                .requestMatchers("/api/**").anonymous()
                .requestMatchers("/app/redis/**").anonymous()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        //退出处理
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter(), JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter(), LogoutFilter.class);
        return httpSecurity.build();
    }


    @Bean
    @Qualifier("captchaUserDetailsService")
    public UserDetailsService captchaUserDetailsService() {
        log.info(">>>>>>>captchaUserDetailsService");
        return phone -> new LoginUser(new HashSet<>());
    }

    @Bean
    @Qualifier("userDetailsServiceImpl")
    public UserDetailsService userDetailsServiceImpl() {
        log.info(">>>>>>>userDetailsServiceImpl");
        return phone -> new LoginUser(new HashSet<>());
    }


    @Bean
    @Qualifier("smsService")
    public ISmsService smsService() {
        log.info(">>>>>>>smsService");
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

    /**
     * 验证码认证器.
     */
    @Bean
    public CaptchaAuthenticationProvider captchaAuthenticationProvider(ISmsService smsService,
                                                                       @Qualifier("captchaUserDetailsService") UserDetailsService userDetailsService
    ) {
        return new CaptchaAuthenticationProvider(userDetailsService, smsService);
    }




    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * 身份认证接口
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder();
//        auth.authenticationProvider(captchaAuthenticationProvider);
//    }


}
