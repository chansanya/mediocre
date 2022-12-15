package com.chansan.domain.system.domain.other;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chansan.domain.base.DelFlag;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 参数配置表 sys_config
 * 
 * @author rourou
 */
@TableName("sys_config")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends DelFlag {

    /** 参数主键 */
    private Long configId;

    /** 参数名称 */
    private String configName;

    /** 参数键名 */
    private String configKey;

    /** 参数键值 */
    private String configValue;

    /** 系统内置（Y是 N否） */
    private String configType;


}
