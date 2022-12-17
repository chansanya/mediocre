package com.chansan.extension.runtime.impl;

import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.chansan.extension.runtime.IRunTimeService;
import com.chansan.extension.runtime.real.RealModel;
import com.chansan.extension.runtime.real.enums.Model;

/**
 * @name: IRunTimeServiceImplTest
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.impl.IRunTimeServiceImplTest
 * @date: 2022/12/17
 * @description:
 */
class IRunTimeServiceImplTest {

    IRunTimeService runTimeService = new IRunTimeServiceImpl();


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
        runTimeService.exec(cmd);

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

        runTimeService.exec(cmd);

        while (true){

        }

    }


}