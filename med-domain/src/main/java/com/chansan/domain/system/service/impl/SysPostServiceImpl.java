package com.chansan.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chansan.domain.system.domain.SysPost;
import com.chansan.domain.system.mapper.SysPostMapper;
import com.chansan.domain.system.service.ISysPostService;

/**
 * @name: SysPostServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.modules.system.service.impl.SysPostServiceImpl
 * @date: 2022/12/15
 * @description:
 */
@Service
public class SysPostServiceImpl  extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

}
