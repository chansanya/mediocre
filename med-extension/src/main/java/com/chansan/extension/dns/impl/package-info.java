/**
 * @name: package-info
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.package-info
 * @date: 2022/12/16
 * @description: DNS解析相关
 */
package com.chansan.extension.dns.impl;

import com.aliyun.teaopenapi.models.Config;

class Rep{
    private static final Integer SUCCESS_CODE = 200;

    /**
     *  失败抛出异常,成功不做处理
     * @param code 状态码
     * @param operate 操作结果
     */
    public static void fail(int code, String operate){
        if(SUCCESS_CODE == code){
            return ;
        }
        throw new RuntimeException(String.format("%s,处理失败.",operate));
    }
}

class AliDnsConfig{
    private static final Config CONFIG;

    static {
        CONFIG = new Config();
        CONFIG.setAccessKeyId("").setAccessKeySecret("");
    }

    public static Config getConfig(){
        return CONFIG;
    }
}