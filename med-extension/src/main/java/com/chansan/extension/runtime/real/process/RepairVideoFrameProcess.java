package com.chansan.extension.runtime.real.process;

import java.io.File;

import com.chansan.extension.runtime.core.process.base.AbstractChainProcess;
import com.chansan.extension.runtime.core.process.base.AbstractFileProcess;
import com.chansan.extension.runtime.real.util.CmdGenUtil;

/**
 * @name: RepairVideoFrameProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.process.RepairVideoFrameProcess
 * @date: 2022/12/19
 * @description: 修复视频帧
 */
public final class RepairVideoFrameProcess extends AbstractFileProcess {

    private  File tempDir;

    public RepairVideoFrameProcess(AbstractChainProcess next,String rootPath, File tempDir,File outDir){
        this(next,CmdGenUtil.repairVideoFramesCmd(rootPath, tempDir, outDir));
    }
    public RepairVideoFrameProcess(AbstractChainProcess next, String cmd) {
        super(next,cmd);
    }


    /**
     * 程序执行成功处理
     *
     * @param result 执行成功消息
     */
    @Override
    protected void success(String result) {
        super.success(result);

        System.out.println("程序结束,删除临时文件");
        delFiles(tempDir);
    }

}
