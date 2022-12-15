package com.chansan.modules.sms;

import com.chansan.common.base.resp.AjaxResult;

/**
 * @name: ISmsService
 * @author: leihuangyan
 * @classPath: com.chansan.modules.sms.ISmsService
 * @date: 2022/12/14
 * @description:
 */
public interface ISmsService {

    /**
     * 发送验证码
     * @param phoneNumbers 手机号
     * @return AjaxResult
     */
    AjaxResult sendCheckCode(String phoneNumbers);

    /**
     * 发送消息
     * @param content content
     * @param phoneNumbers 手机号数组
     * @return AjaxResult
     */
    AjaxResult sendMsg(String content,String... phoneNumbers);

    /**
     * 校验验证码
     * @param phone 手机号
     * @param code 验证码
     * @return  是否匹配
     */
    boolean verifyCaptcha(String phone, String code);
}
