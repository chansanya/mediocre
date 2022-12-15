package com.chansan.domain.system.domain.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chansan.domain.base.DelFlag;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户对象 sys_user
 * 
 * @author yf
 */
@TableName("sys_user")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUser extends DelFlag {

    private Long userId;
    private Long deptId;
    private String userName;
    private String nickName;
    private String userType;
    private String email;
    private String phonenumber;
    private String sex;
    private String avatar;
    private String password;
    private String status;

}
