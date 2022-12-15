package com.chansan.core.security.userdetails;

import java.util.HashSet;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chansan.modules.system.domain.user.SysUser;
import com.chansan.modules.system.domain.login.LoginUser;
import com.chansan.modules.system.service.ISysUserService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @name: CaptchaUserDetailsService
 * @author: leihuangyan
 * @classPath: com.chansan.core.security.userdetails.CaptchaUserDetailsService
 * @date: 2022/12/15
 * @description:
 */
@Slf4j
@Service
public class CaptchaDetailsServiceImpl implements UserDetailsService {


    @Resource
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> captchaUserDetailsService");

        SysUser sysUser = sysUserService.getUserInfoByPhone(phone);

        return new LoginUser(sysUser,new HashSet<>());
    }
}
