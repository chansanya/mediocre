package com.chansan.domain.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery;
import com.chansan.domain.system.domain.SysUserRole;
import com.chansan.domain.system.domain.role.SysRole;
import com.chansan.domain.system.mapper.SysUserRoleMapper;
import com.chansan.domain.system.service.ISysUserRoleService;

/**
 * @name: SysUserRoleServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.service.impl.SysUserRoleServiceImpl
 * @date: 2022/12/15
 * @description:
 */
@Service
public class SysUserRoleServiceImpl  extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {


    /**
     * 根据 角色ID列表 得到用户角色
     *
     * @param roleIds roleIds
     * @return Role
     */
    @Override
    public List<SysRole> listRoleByRoleIds(List<Long> roleIds) {
        return SimpleQuery.selectList(SysRole.class, new LambdaQueryWrapper<SysRole>().in(SysRole::getRoleId, roleIds));
    }
}
