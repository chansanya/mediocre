package com.chansan.extension.gen.model;

import java.util.List;

import com.chansan.extension.gen.util.VelocityUtils;

import lombok.Builder;
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
public class FrpClientModel extends BaseModel {

    /**
     * 服务器地址
     */
    private String serverAddr;
    /**
     * 服务器端口
     */
    private int serverPort;
    /**
     * 访问端口
     */
    private int visitPort;
    /**
     * 令牌
     */
    private String token;
    /**
     * 需要穿透的服务
     */
    private List<Service> services;

    public FrpClientModel() {
        this.setTemplatePath(VelocityUtils.getFrpClientConfigVmPath());
    }

    /**
     * 客户端服务配置
     */
    @Data
    @Builder
    @Accessors(chain = true)
    public static class Service {
        /**
         * 名字
         */
        private String name;
        /**
         * 服务器地址
         */
        private String type;
        /**
         * 本地服务器地址
         */
        private int localPort;
        /**
         * 域名 需要解析到 FrpClientModel#serverAddr
         */
        private String domain;
    }

}
