package com.chansan.domain.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chansan.domain.system.domain.user.SysUser;

/**
 * @name: SysUserMapper
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.mapper.SysUserMapper
 * @date: 2022/12/15
 * @description:
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据名字得到用户信息
     * @param name 用户名
     * @return SysUser
     */
    default SysUser getUserInfoByName(String name){
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, name));
    }

    /**
     * 根据名字得到用户信息
     * @param phone 用户名
     * @return SysUser
     */
    default SysUser getUserInfoByPhone(String phone){
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, phone));
    }

}
