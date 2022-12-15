package com.chansan.modules.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chansan.modules.system.domain.SysPost;
import com.chansan.modules.system.domain.SysUserPost;

/**
 * @name: SysUserMapper
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.mapper.SysUserMapper
 * @date: 2022/12/15
 * @description:
 */
public interface ISysUserPostService extends IService<SysUserPost> {


    /**
     * 根据userId得到用户职位
     * @param userId 用户ID
     * @return Post
     */
    List<SysPost> listPostByUserId(Long userId);

}
