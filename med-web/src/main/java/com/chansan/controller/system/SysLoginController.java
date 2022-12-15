package com.chansan.controller.system;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chansan.common.base.resp.AjaxResult;
import com.chansan.modules.system.domain.login.LoginBody;
import com.chansan.modules.system.service.impl.SysLoginService;


import jakarta.annotation.Resource;


/**
 * 登录验证
 *
 * @author yf
 */
@RestController
public class SysLoginController {

    @Resource
    private SysLoginService loginService;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(),loginBody.getPassword());
        return AjaxResult.success((Object)token );
    }


}
