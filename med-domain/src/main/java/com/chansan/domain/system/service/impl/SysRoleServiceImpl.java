package com.chansan.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chansan.domain.system.domain.role.SysRole;
import com.chansan.domain.system.mapper.SysRoleMapper;
import com.chansan.domain.system.service.ISysRoleService;

/**
 * @name: SysRoleServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.service.impl.SysRoleServiceImpl
 * @date: 2022/12/15
 * @description:
 */
@Service
public class SysRoleServiceImpl  extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

}
