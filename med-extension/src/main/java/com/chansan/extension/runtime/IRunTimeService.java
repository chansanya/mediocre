package com.chansan.extension.runtime;

/**
 * @name: IRunTimeService
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.IRunTimeService
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
     * 视频
     * @param cmd cmd
     */
    void video(String cmd);
}
