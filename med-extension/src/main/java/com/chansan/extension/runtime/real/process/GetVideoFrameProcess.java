package com.chansan.extension.runtime.real.process;

import java.io.File;

import com.chansan.extension.runtime.core.process.base.AbstractChainProcess;
import com.chansan.extension.runtime.core.process.base.AbstractFileProcess;
import com.chansan.extension.runtime.real.util.CmdGenUtil;

/**
 * @name: ImgProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.process.ImgProcess
 * @date: 2022/12/19
 * @description: 得到视频帧
 */
public final class GetVideoFrameProcess extends AbstractFileProcess {


    public GetVideoFrameProcess(AbstractChainProcess next, File video, File tempDir) {
        this(next, CmdGenUtil.getVideoFramesCmd(video, tempDir));
    }
    public GetVideoFrameProcess(AbstractChainProcess next, String cmd) {
        super(next,cmd);
    }



}
