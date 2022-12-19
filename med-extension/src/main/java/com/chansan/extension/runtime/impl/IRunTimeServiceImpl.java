package com.chansan.extension.runtime.impl;

import com.chansan.extension.runtime.IRunTimeService;
import com.chansan.extension.runtime.process.ImgProcess;

/**
 * @name: IRunTimeServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.impl.IRunTimeServiceImpl
 * @date: 2022/12/17
 * @description: process
 */
public class IRunTimeServiceImpl implements IRunTimeService {

    /**
     * 执行命令
     *
     * @param cmd cmd
     */
    @Override
    public void img(String cmd) {
        new ImgProcess(cmd).exec();
    }

    /**
     * 视频
     *
     * @param cmd cmd
     */
    @Override
    public void video(String cmd) {


    }
}
