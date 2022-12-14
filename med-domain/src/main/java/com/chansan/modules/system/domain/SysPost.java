package com.chansan.modules.system.domain;

import com.chansan.modules.base.DelFlag;

import lombok.Data;

/**
 * 岗位表 sys_post
 *
 * @author rourou
 */
@Data
public class SysPost extends DelFlag {


    /**
     * 岗位序号
     */
    private Long postId;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位排序
     */
    private String postSort;

    /**
     * 状态（0正常 1停用）
     */
    private String status;


}
