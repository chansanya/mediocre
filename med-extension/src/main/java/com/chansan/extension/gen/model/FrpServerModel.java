package com.chansan.extension.gen.model;

import com.chansan.extension.gen.util.VelocityUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @name: FrpClientModel
 * @author: leihuangyan
 * @classPath: com.chansan.extension.gen.model.FrpClientModel
 * @date: 2022/12/16
 * @description:
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FrpServerModel extends BaseModel{

    /**
     * frp服务端口
     */
    private int bindPort;
    /**
     * 访问端口
     */
    private int visitPort;
    /**
     * 令牌
     */
    private String token;
    /**
     * frp管理后台端口
     */
    private int dashboardPort;
    /**
     * frp管理后台 用户名
     */
    private String dashboardUser;
    /**
     * frp管理后台 密码
     */
    private String dashboardPwd;


    public FrpServerModel() {
        this.setTemplatePath(VelocityUtils.getFrpServerConfigVmPath());
    }

}
