package com.chansan.modules.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chansan.modules.system.domain.SysDept;
import com.chansan.modules.system.domain.SysRoleDept;

/**
 * @name: SysUserMapper
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.mapper.SysUserMapper
 * @date: 2022/12/15
 * @description:
 */
public interface ISysRoleDeptService extends IService<SysRoleDept> {



    /**
     * 根据角色ID列表 得到 部门
     * @param roleIds roleIds
     * @return Dept
     */
    List<SysDept> listDeptByRoleIds(List<Long> roleIds);


}
