package com.chansan.extension.runtime.real.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.chansan.extension.exception.RealException;
import com.chansan.extension.runtime.real.process.CopyVideoAudioProcess;
import com.chansan.extension.runtime.real.process.MergeVideoFrameProcess;
import com.chansan.extension.runtime.real.process.VideoFpsProcess;

import lombok.Builder;

/**
 * @name: ChainBuilder
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.util.ChainBuilder
 * @date: 2022/12/19
 * @description:
 */
@Builder
public class ChainBuilder {

    private String rootPath;
    private File tempDir;
    private File outDir;
    private File video;


    private File outVideo;
    private File outAudioVideo;
    private String fps;


    public void exec() {
        exec(null);
    }

    public void exec(String fps) {

        check();

        if (StringUtils.isBlank(fps)) {
            try {
                //得到视频pfs
                VideoFpsProcess videoFpsProcess = new VideoFpsProcess(CmdGenUtil.getVideoFpsCmd(video));
                videoFpsProcess.exec();
                Thread.sleep(3000);
                fps = videoFpsProcess.getFps();

            } catch (InterruptedException e) {
                throw new RealException("获取视频:{0} fps异常");
            }
        }
        new MergeVideoFrameProcess(
                //导入音频
                new CopyVideoAudioProcess(fps, video, outDir, outAudioVideo),
                fps, outDir, outVideo
        ).exec();

//        //得到视频帧
//         new GetVideoFrameProcess(
//                //修复视频帧
//                new RepairVideoFrameProcess(
//                        //合并视频
//                        new MergeVideoFrameProcess(
//                                //导入音频
//                                new CopyVideoAudioProcess(fps, video, outDir, outAudioVideo),
//                                fps, outDir, outVideo
//                        ),
//                        rootPath, tempDir, outDir
//                ),
//                 video, tempDir
//        ).exec();
    }

    private void check() {
        if (StringUtils.isBlank(rootPath)) {
            throw new RealException("执行文件根目录不能为空");
        }
        if (!tempDir.exists()) {
            boolean mkdir = tempDir.mkdirs();
            System.out.printf("创建临时文件夹:%s => %b", tempDir, mkdir);
            System.out.println();
        }

        if (!outDir.exists()) {
            boolean mkdir = outDir.mkdirs();
            System.out.printf("创建修复文件输出文件夹:%s => %b", outDir, mkdir);
            System.out.println();
        }

        if (!video.exists()) {
            throw new RealException("文件:{0}不存在", video);
        }

        if (outVideo.exists()) {
            throw new RealException("文件已生成", outVideo);
        }
        if (outAudioVideo.exists()) {
            throw new RealException("带音频文件已生成", outAudioVideo);
        }
    }

}
