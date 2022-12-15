package com.chansan.domain.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chansan.domain.system.domain.SysMenu;
import com.chansan.domain.system.domain.SysRoleMenu;

/**
 * @name: SysUserMapper
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.mapper.SysUserMapper
 * @date: 2022/12/15
 * @description:
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {



    /**
     * 根据角色ID列表 得到菜单
     * @param roleIds roleIds
     * @return Menu
     */
    List<SysMenu> listMenuByRoleIds(List<Long> roleIds);

}
