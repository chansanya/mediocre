package com.chansan.extension.runtime.process;

import com.chansan.extension.runtime.process.base.AbstractChainProcess;

/**
 * @name: MergeVideoFrameProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.process.MergeVideoFrameProcess
 * @date: 2022/12/19
 * @description:
 */
public final class MergeVideoFrameProcess  extends AbstractChainProcess {

    public MergeVideoFrameProcess(AbstractChainProcess next, String cmd) {
        super(next,cmd);
    }


    public MergeVideoFrameProcess(String cmd) {
        super(null, cmd);
    }


}
