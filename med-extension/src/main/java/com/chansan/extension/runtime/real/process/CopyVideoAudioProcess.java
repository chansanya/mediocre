package com.chansan.extension.runtime.real.process;

import java.io.File;

import com.chansan.extension.runtime.core.process.base.AbstractChainProcess;
import com.chansan.extension.runtime.core.process.base.AbstractFileProcess;
import com.chansan.extension.runtime.real.util.CmdGenUtil;

/**
 * @name: CopyVideoAudioProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.process.CopyVideoAudioProcess
 * @date: 2022/12/19
 * @description: 导入音频
 */
public final class CopyVideoAudioProcess extends AbstractFileProcess {


    private File video;
    private File outDir;

    public CopyVideoAudioProcess(String fps,File video,File outDir,File outAudioVideo) {
        this(null, CmdGenUtil.copyVideoAudio(fps, video, outDir, outAudioVideo));
        this.video = video;
        this.outDir = outDir;
    }

    @SuppressWarnings("unused")
    public CopyVideoAudioProcess(String cmd) {
        this(null, cmd);
    }

    public CopyVideoAudioProcess(AbstractChainProcess next, String cmd) {
        super(next, cmd);
    }

    /**
     * 程序执行成功处理
     *
     * @param result 执行成功消息
     */
    @Override
    protected void success(String result) {
        super.success(result);

        System.out.println("程序结束,删除处理文件");
        delFiles(outDir);
//        delFiles(video,outDir);
    }
}
