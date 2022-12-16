package com.chansan.extension.gen.util;

import java.util.ArrayList;
import java.util.List;


/**
 * @name: VelocityUtils
 * @author: leihuangyan
 * @classPath: com.chansan.extension.gen.util.VelocityUtils
 * @date: 2022/12/16
 * @description: 模板处理工具类
 */
public class VelocityUtils {


    /**
     * 得到Frp客户端配置文件
     * @return  文件resources下路径
     */
    public static String getFrpClientConfigVmPath() {
        return "vm/frp/frpc.ini.vm";
    }

    /**
     * 得到Frp服务端配置文件
     * @return  文件resources下路径
     */
    public static String getFrpServerConfigVmPath() {
        return "vm/frp/frps.ini.vm";
    }


    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList() {
        List<String> templates = new ArrayList<>();
        templates.add("vm/frp/frpc.ini.vm");
        return templates;
    }



}
