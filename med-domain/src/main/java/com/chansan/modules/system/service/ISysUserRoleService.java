package com.chansan.modules.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chansan.modules.system.domain.SysUserRole;
import com.chansan.modules.system.domain.role.SysRole;

/**
 * @name: SysUserMapper
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.mapper.SysUserMapper
 * @date: 2022/12/15
 * @description:
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据 角色ID列表 得到用户角色
     * @param roleIds roleIds
     * @return Role
     */
    List<SysRole> listRoleByRoleIds(List<Long> roleIds);


}
