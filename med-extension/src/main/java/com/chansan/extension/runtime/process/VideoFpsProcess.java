package com.chansan.extension.runtime.process;

import com.chansan.extension.runtime.process.base.AbstractChainProcess;

/**
 * @name: VideoFpsProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.process.VideoFpsProcess
 * @date: 2022/12/19
 * @description: 得到视频fps，这个类很离谱, 不过处理过也没啥问题，先用着
 */
public final class VideoFpsProcess extends AbstractChainProcess {

    /**
     * video fps
     */
    private String fps;

    public VideoFpsProcess(String cmd) {
        super(null,cmd);
    }

    /**
     * 程序错误时处理
     *
     * @param error 程序打印的错误消息
     */
    @Override
    protected void error(String error) {
        this.success(parseFps(error));
    }

    /**
     * 程序执行成功处理
     *
     * @param result 执行成功消息
     */
    @Override
    protected void success(String result) {
        System.out.println("得到fps:"+result);
        this.fps = result;
    }


    private String parseFps(String result){
        //根据ffmpeg -i 命令适配的硬编码
        return result.substring(result.indexOf("fps,") - 6, result.indexOf("fps,") - 1);
    }

    public String getFps() {
        return fps;
    }
}
