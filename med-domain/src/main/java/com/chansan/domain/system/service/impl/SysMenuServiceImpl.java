package com.chansan.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chansan.domain.system.domain.SysMenu;
import com.chansan.domain.system.mapper.SysMenuMapper;
import com.chansan.domain.system.service.ISysMenuService;

/**
 * @name: SysMenuServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.service.impl.SysMenuServiceImpl
 * @date: 2022/12/15
 * @description:
 */

@Service
public class SysMenuServiceImpl  extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

}
