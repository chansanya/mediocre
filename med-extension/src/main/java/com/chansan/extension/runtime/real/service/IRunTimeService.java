package com.chansan.extension.runtime.real.service;

import com.chansan.extension.runtime.real.util.ChainBuilder;

/**
 * @name: IRunTimeService
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.core.service.IRunTimeService
 * @date: 2022/12/17
 * @description:
 */
public interface IRunTimeService {

    /**
     * 执行命令
     * @param cmd cmd
     */
    void img(String cmd);

    /**
     *  视频
     * @param chainBuilder chainBuilder
     */
    void video(ChainBuilder chainBuilder);
}
