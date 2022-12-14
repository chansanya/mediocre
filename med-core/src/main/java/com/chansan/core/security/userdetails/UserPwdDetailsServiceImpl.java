package com.chansan.core.security.userdetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery;
import com.chansan.common.exception.ServiceException;
import com.chansan.common.utils.ApplicationContext;
import com.chansan.domain.system.domain.SysDept;
import com.chansan.domain.system.domain.SysMenu;
import com.chansan.domain.system.domain.SysPost;
import com.chansan.domain.system.domain.SysUserRole;
import com.chansan.domain.system.domain.login.LoginUser;
import com.chansan.domain.system.domain.role.SysRole;
import com.chansan.domain.system.service.ISysRoleDeptService;
import com.chansan.domain.system.service.ISysRoleMenuService;
import com.chansan.domain.system.service.ISysUserPostService;
import com.chansan.domain.system.service.ISysUserRoleService;
import com.chansan.domain.system.service.ISysUserService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @name: UserwPwdDetailsService
 * @author: leihuangyan
 * @classPath: com.chansan.core.security.userdetails.UserwPwdDetailsService
 * @date: 2022/12/15
 * @description:
 */
@Slf4j
@Service
public class UserPwdDetailsServiceImpl implements UserDetailsService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private ISysUserPostService sysUserPostService;
    @Resource
    private ISysRoleDeptService sysRoleDeptService;
    @Resource
    private ISysRoleMenuService sysRoleMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> UserPwdDetailsService");

        var user = sysUserService.getUserInfoByName(username);

        if(null == user){
            throw new ServiceException("???????????????");
        }

        if(ApplicationContext.isAdmin(user.getUserId())){
            return new LoginUser(user,Set.of("*:*:*"));
        }

        var userId = user.getUserId();

        List<Long> roleIds = SimpleQuery.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId), SysUserRole::getRoleId);

        List<SysPost> sysPosts = sysUserPostService.listPostByUserId(userId);
        List<SysRole> sysRoles = sysUserRoleService.listRoleByRoleIds(roleIds);
        List<SysDept> sysDepts = sysRoleDeptService.listDeptByRoleIds(roleIds);
        List<SysMenu> sysMenus = sysRoleMenuService.listMenuByRoleIds(roleIds);


        return new LoginUser(user,new HashSet<>());
    }
}
