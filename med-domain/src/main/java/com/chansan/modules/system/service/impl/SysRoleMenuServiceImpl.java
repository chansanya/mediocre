package com.chansan.modules.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chansan.modules.system.domain.SysMenu;
import com.chansan.modules.system.domain.SysRoleMenu;
import com.chansan.modules.system.mapper.SysRoleMenuMapper;
import com.chansan.modules.system.service.ISysRoleMenuService;

/**
 * @name: SysRoleMenuServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.service.impl.SysRoleMenuServiceImpl
 * @date: 2022/12/15
 * @description:
 */
@Service
public class SysRoleMenuServiceImpl  extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {


    /**
     * 根据角色ID列表 得到菜单
     *
     * @param roleIds roleIds
     * @return Menu
     */
    @Override
    public List<SysMenu> listMenuByRoleIds(List<Long> roleIds) {
        return null;
    }
}
