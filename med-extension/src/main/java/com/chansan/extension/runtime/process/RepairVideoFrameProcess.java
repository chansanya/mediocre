package com.chansan.extension.runtime.process;

import com.chansan.extension.runtime.process.base.AbstractChainProcess;

/**
 * @name: RepairVideoFrameProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.process.RepairVideoFrameProcess
 * @date: 2022/12/19
 * @description:
 */
public final class RepairVideoFrameProcess extends AbstractChainProcess {

    public RepairVideoFrameProcess(AbstractChainProcess next, String cmd) {
        super(next,cmd);
    }



}
