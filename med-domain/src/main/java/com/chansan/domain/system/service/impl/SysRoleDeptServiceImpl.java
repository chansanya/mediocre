package com.chansan.domain.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chansan.domain.system.domain.SysDept;
import com.chansan.domain.system.domain.SysRoleDept;
import com.chansan.domain.system.mapper.SysRoleDeptMapper;
import com.chansan.domain.system.service.ISysRoleDeptService;

/**
 * @name: SysRoleDeptServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.service.impl.SysRoleDeptServiceImpl
 * @date: 2022/12/15
 * @description:
 */
@Service
public class SysRoleDeptServiceImpl  extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements ISysRoleDeptService {


    /**
     * 根据角色ID列表 得到 部门
     *
     * @param roleIds roleIds
     * @return Dept
     */
    @Override
    public List<SysDept> listDeptByRoleIds(List<Long> roleIds) {
        return null;
    }
}
