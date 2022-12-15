package com.chansan.modules.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery;
import com.chansan.modules.system.domain.SysPost;
import com.chansan.modules.system.domain.SysUserPost;
import com.chansan.modules.system.mapper.SysUserPostMapper;
import com.chansan.modules.system.service.ISysUserPostService;

/**
 * @name: SysUserPostServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.service.impl.SysUserPostServiceImpl
 * @date: 2022/12/15
 * @description:
 */
@Service
public class SysUserPostServiceImpl  extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {


    /**
     * 根据userId得到用户职位
     *
     * @param userId 用户ID
     * @return Post
     */
    @Override
    public List<SysPost> listPostByUserId(Long userId) {
        List<Long> postIds = SimpleQuery.list(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId, userId), SysUserPost::getPostId);
        return SimpleQuery.selectList(SysPost.class, new LambdaQueryWrapper<SysPost>().in(SysPost::getPostId, postIds));
    }
}
