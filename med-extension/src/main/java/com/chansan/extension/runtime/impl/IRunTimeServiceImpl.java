package com.chansan.extension.runtime.impl;

import com.chansan.extension.runtime.IRunTimeService;
import com.chansan.extension.runtime.util.RunTimeUtil;

/**
 * @name: IRunTimeServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.impl.IRunTimeServiceImpl
 * @date: 2022/12/17
 * @description:
 */
public class IRunTimeServiceImpl implements IRunTimeService {

    /**
     * 执行命令
     *
     * @param cmd cmd
     */
    @Override
    public void exec(String cmd) {

//        RunTimeUtil.run(cmd);
        RunTimeUtil.runAsync(cmd);

    }
}
