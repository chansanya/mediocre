package com.chansan.extension.runtime.impl;

import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.chansan.extension.exception.RealException;
import com.chansan.extension.runtime.IRunTimeService;
import com.chansan.extension.runtime.process.CopyVideoAudioProcess;
import com.chansan.extension.runtime.process.GetVideoFrameProcess;
import com.chansan.extension.runtime.process.MergeVideoFrameProcess;
import com.chansan.extension.runtime.process.RepairVideoFrameProcess;
import com.chansan.extension.runtime.process.VideoFpsProcess;
import com.chansan.extension.runtime.real.RealModel;
import com.chansan.extension.runtime.real.enums.Model;
import com.chansan.extension.runtime.real.util.CmdGenUtil;

/**
 * @name: IRunTimeServiceImplTest
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.impl.IRunTimeServiceImplTest
 * @date: 2022/12/17
 * @description:
 */
class IRunTimeServiceImplTest {

    IRunTimeService runTimeService = new IRunTimeServiceImpl();

    private static  final  String rootPath = "E:\\Users\\yf\\Downloads\\pdf\\test\\realesrgan-ncnn-vulkan-20220424-windows";

    private static  final  String videoPath = MessageFormat.format("{0}\\demo1.mp4", rootPath);

    private static  final  String uuid = UUID.randomUUID().toString().replace("-","");
//    private static  final  String uuid = "b8bbbf1e82ce48ccb2d1945a20be3a28";

    private static  final  String tempDirPath = MessageFormat.format("{0}\\tmp_frames\\{1}\\",rootPath,uuid);
    private static  final  String outDirPath = MessageFormat.format("{0}\\out_frames\\{1}\\",rootPath,uuid);
    private static  final  String outVideoPath = MessageFormat.format("{0}\\target\\{1}.mp4", rootPath, uuid);
    private static  final  String outAudioVideoPath = MessageFormat.format("{0}\\target\\audio-{1}.mp4", rootPath, uuid);

    @Test
    public void run(){
//        String cmd = "ping lovefile.cn";
        String rootPath = "E:\\Users\\yf\\Downloads\\pdf\\test\\realesrgan-ncnn-vulkan-20220424-windows";

//        String sourceFile = String.format("%s\\input.jpg",rootPath);
        String sourceFile = String.format("%s\\test.jpeg",rootPath);
        System.out.println("源文件"+sourceFile);

        String targetFile = String.format("%s\\target\\%s.png",rootPath,System.currentTimeMillis());
//        String targetFile = String.format("%s\\target\\java生成.png",rootPath);
        System.out.println("目标文件"+targetFile);

        String extend = "-n realesr-animevideov3 -v";
        System.out.println("算法模型"+extend);

        String cmd = String.format("%s\\realesrgan-ncnn-vulkan.exe -i %s -o %s %s",rootPath,sourceFile,targetFile,extend);
//        String cmd = "ping lovefile.cn";
//        String cmd = "ipconfig";
//        String cmd = "java --version";

        System.out.println("当前命令:"+cmd);
        runTimeService.img(cmd);

        while (true){

        }

    }

    @Test
    public void img(){
//        String cmd = "ping lovefile.cn";
        String rootPath = "E:\\Users\\yf\\Downloads\\pdf\\test\\realesrgan-ncnn-vulkan-20220424-windows";

        String sourcePath = MessageFormat.format("{0}/test.jpeg", rootPath);
        String targetPath = MessageFormat.format("{0}/target/{1}.jpeg", rootPath, UUID.randomUUID());

        System.out.println("sourcePath:"+sourcePath);
        System.out.println("targetPath:"+targetPath);

        String cmd = RealModel.builder()
                .rootPath(rootPath)
                .model(Model.ANIME_VIDEO)
                .source(new File(sourcePath))
                .target(new File(targetPath))
                .build()
                .getImgCmd();

        System.out.println("执行命令:"+cmd);

        runTimeService.img(cmd);

        stop();

    }



    @Test
    public void video() throws InterruptedException {



        File tempDir = new File(tempDirPath);
        if(!tempDir.exists()){
            boolean mkdir = tempDir.mkdirs();
            System.out.printf("创建文件夹:%s => %b",tempDir,mkdir);
            System.out.println();
        }


        File outDir = new File(outDirPath);
        if(!outDir.exists()){
            boolean mkdir = outDir.mkdirs();
            System.out.printf("创建文件夹:%s => %b",outDir,mkdir);
            System.out.println();
        }

        File video = new File(videoPath);
        if(!video.exists()){
           throw new RealException("文件:{0}不存在",videoPath);
        }

        File outVideo = new File(outVideoPath);
        File outAudioVideo = new File(outAudioVideoPath);

        VideoFpsProcess videoFpsProcess = new VideoFpsProcess(CmdGenUtil.getVideoFpsCmd(new File(videoPath)));
        videoFpsProcess.exec();
        Thread.sleep(3000);
        String fps = videoFpsProcess.getFps();
        System.out.printf("视频的FPS:%s",fps);
        System.out.println();

        CopyVideoAudioProcess copyVideoAudioProcess = new CopyVideoAudioProcess(
                null,CmdGenUtil.copyVideoAudio(fps,video,outDir,outAudioVideo)
        );

        MergeVideoFrameProcess mergeVideoFrameProcess = new MergeVideoFrameProcess(
                copyVideoAudioProcess,CmdGenUtil.mergeVideoFramesCmd(fps,outDir,outVideo)
        );

//        mergeVideoFrameProcess.exec();
        RepairVideoFrameProcess repairVideoFrameProcess = new RepairVideoFrameProcess(
                mergeVideoFrameProcess,CmdGenUtil.repairVideoFramesCmd(rootPath,tempDir,outDir)
        );

        GetVideoFrameProcess getVideoFrameProcess = new GetVideoFrameProcess(
                repairVideoFrameProcess,CmdGenUtil.getVideoFramesCmd(video,tempDir)
        );

        getVideoFrameProcess.exec();

        stop();
    }

    @Test
    public void getFps(){
        VideoFpsProcess videoFpsProcess = new VideoFpsProcess(CmdGenUtil.getVideoFpsCmd(new File(videoPath)));
        videoFpsProcess.exec();
        System.out.printf("视频的FPS:%s",videoFpsProcess.getFps());
        stop();
    }



    private static void stop() {
        while (true){

        }
    }

}