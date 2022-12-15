package com.chansan.domain.vps.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @name: Vps
 * @author： rourou
 * @classPath: com.rourou.system.domain.Vps
 * @date: 2022-11-18
 * @Version: 1.0
 * @description: 文件描述:VPS对象 t_vps
 */

@TableName("t_vps")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Vps extends Model<Vps> {

    /** ID*/
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 节点名*/

    @TableField(value = "`vps_name`")
    private String vpsName;

    /** 地址*/

    @TableField(value = "`host`")
    private String host;

    /** 端口*/

    @TableField(value = "`port`")
    private String port;

    /** 协议*/
    @TableField(value = "`protocol`")
    private String protocol;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
