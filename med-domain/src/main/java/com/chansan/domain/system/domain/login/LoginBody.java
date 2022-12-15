package com.chansan.domain.system.domain.login;

import java.io.Serializable;

import lombok.Data;

/**
 * @name: LoginBody
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.domain.login.LoginBody
 * @date: 2022/12/15
 * @description:
 */
@Data
public class LoginBody implements Serializable {

    private String username;
    private String password;
}
