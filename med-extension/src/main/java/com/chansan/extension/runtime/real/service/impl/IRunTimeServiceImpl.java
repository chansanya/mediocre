package com.chansan.extension.runtime.real.service.impl;

import com.chansan.extension.runtime.real.service.IRunTimeService;
import com.chansan.extension.runtime.real.process.ImgProcess;
import com.chansan.extension.runtime.real.util.ChainBuilder;

/**
 * @name: IRunTimeServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.core.impl.IRunTimeServiceImpl
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
     * @param chainBuilder chainBuilder
     */
    @Override
    public void video(ChainBuilder chainBuilder) {
        chainBuilder.exec();
    }
}
