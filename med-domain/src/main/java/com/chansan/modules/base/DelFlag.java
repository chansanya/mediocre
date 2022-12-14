package com.chansan.modules.base;

import lombok.Data;

/**
 * Entity基类
 * 
 * @author rourou
 */
@Data
public class DelFlag extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 逻辑删除 */
    private Boolean flag;

}
