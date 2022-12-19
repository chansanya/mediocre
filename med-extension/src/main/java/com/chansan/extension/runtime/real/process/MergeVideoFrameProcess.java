package com.chansan.extension.runtime.real.process;

import java.io.File;

import com.chansan.extension.runtime.core.process.base.AbstractChainProcess;
import com.chansan.extension.runtime.core.process.base.AbstractFileProcess;
import com.chansan.extension.runtime.real.util.CmdGenUtil;

/**
 * @name: MergeVideoFrameProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.process.MergeVideoFrameProcess
 * @date: 2022/12/19
 * @description: 合并视频
 */
public final class MergeVideoFrameProcess  extends AbstractFileProcess {



    public MergeVideoFrameProcess(AbstractChainProcess next,String rootPath, File outDir, File outVideo) {
        this(next, CmdGenUtil.mergeVideoFramesCmd(rootPath, outDir, outVideo));
    }

    public MergeVideoFrameProcess(AbstractChainProcess next, String cmd) {
        super(next,cmd);
    }


    public MergeVideoFrameProcess(String cmd) {
        super(null, cmd);
    }


}
