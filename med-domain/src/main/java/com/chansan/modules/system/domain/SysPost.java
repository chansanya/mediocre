package com.chansan.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chansan.modules.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 岗位表 sys_post
 *
 * @author rourou
 */
@TableName("sys_post")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysPost extends BaseEntity {


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
