package com.chansan.domain.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity基类
 * 
 * @author rourou
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DelFlag extends BaseEntity {
    /** 逻辑删除 */
    private Boolean delFlag;

}
