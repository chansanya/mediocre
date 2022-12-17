package com.chansan.extension.runtime.real.util;

import java.io.File;
import java.text.MessageFormat;
import java.util.Optional;

import com.chansan.extension.runtime.real.enums.ImageFormat;
import com.chansan.extension.runtime.real.enums.Model;

import lombok.Data;

/**
 * @name: CmdGenUtil
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.util.CmdGenUtil
 * @date: 2022/12/17
 * @description:  命令构建
 * <a href="https://github.com/xinntao/Real-ESRGAN/blob/master/README_CN.md">...</a>
 * 模型介绍
 * <a href="https://github.com/xinntao/Real-ESRGAN/blob/master/docs/anime_video_model.md">...</a>
 * 比较
 * <a href="https://github.com/xinntao/Real-ESRGAN/blob/master/docs/anime_comparisons_CN.md">...</a>
 */
@SuppressWarnings("unused")
@Data
public class CmdGenUtil {

    private static final int MAX_SCALE = 4;

    private static final int MIN_SCALE = 2;


    public static String getExecPath(String rootPath){
        return MessageFormat.format("{0}/realesrgan-ncnn-vulkan.exe",rootPath);
    }

    /**
     * 修改
     * @param rootPath    exec rootPath
     * @param source 源图片
     * @param target  目标图片
     * @param model   模型
     * @return 命令
     */
    public static String repairImg(String rootPath,File source, File target, Model model) {
        return repair(rootPath,source, target, model, MIN_SCALE, ImageFormat.PNG);
    }


    /**
     * 修改
     *
     * @param rootPath    exec rootPath
     * @param tempDir     视频帧 临时文件夹
     * @param outDir      修复后的视频帧 输出文件夹
     * @param model       模型
     * @param scale       可用 2, 3, 4. 默认 4
     * @param imageFormat jpg/png/webp 默认png
     * @return 命令
     */
    public static String repair(String rootPath,File tempDir, File outDir, Model model, int scale, ImageFormat imageFormat) {

        if (MIN_SCALE > scale) {
            scale = MIN_SCALE;
        }

        if (MAX_SCALE < scale) {
            scale = MAX_SCALE;
        }

        if (null == imageFormat) {
            return MessageFormat.format("{0} -i {1} -o {2} -n {3} -s {4} -v",
                    getExecPath(rootPath),
                    tempDir.getAbsolutePath(), outDir.getAbsolutePath(),
                    //默认 ANIME_VIDEO
                    Optional.ofNullable(model).orElse(Model.ANIME_VIDEO).getModel(), scale
            );
        }

        return MessageFormat.format("{0} -i {1} -o {2} -n {3} -s {4} -f {5} -v",
                getExecPath(rootPath),
                tempDir.getAbsolutePath(), outDir.getAbsolutePath(),
                //默认 ANIME_VIDEO
                Optional.ofNullable(model).orElse(Model.ANIME_VIDEO).getModel(), scale, imageFormat.getFormat()
        );
    }



    /**
     * 第一步:得到视频帧
     * 参考命令
     * ffmpeg -i onepiece_demo.mp4 -qscale:v 1 -qmin 1 -qmax 1 -vsync 0 tmp_frames/frame%08d.png
     *
     * @param video   视频
     * @param tempDir 视频帧 临时文件夹
     * @return 命令
     */
    public static String getVideoFramesCmd(File video, File tempDir) {
        return MessageFormat.format("ffmpeg -i {0} -qscale:v 1 -qmin 1 -qmax 1 -vsync 0 {1}/frame%08d.png",
                video.getAbsolutePath(), tempDir.getAbsolutePath()
        );
    }

    /**
     * 第二步:修复视频命令
     * 参考命令
     * realesrgan-ncnn-vulkan.exe -i tmp_frames -o out_frames -n realesr-animevideov3 -s 2 -f jpg
     * Usage: realesrgan-ncnn-vulkan.exe -i infile -o outfile [options]...
     * -h                   show this help
     * -i input-path        input image path (jpg/png/webp) or directory
     * -o output-path       output image path (jpg/png/webp) or directory
     * -s scale             upscale ratio (can be 2, 3, 4. default=4)
     * -t tile-size         tile size (>=32/0=auto, default=0) can be 0,0,0 for multi-gpu
     * -m model-path        folder path to the pre-trained models. default=models
     * -n model-name        model name (default=realesr-animevideov3, can be realesr-animevideov3 | realesrgan-x4plus | realesrgan-x4plus-anime | realesrnet-x4plus)
     * -g gpu-id            gpu device to use (default=auto) can be 0,1,2 for multi-gpu
     * -j load:proc:save    thread count for load/proc/save (default=1:2:2) can be 1:2,2,2:2 for multi-gpu
     * -x                   enable tta mode"
     * -f format            output image format (jpg/png/webp, default=ext/png)
     * -v                   verbose output
     *
     * @param rootPath 执行文件根目录
     * @param tempDir 视频帧 临时文件夹
     * @param outDir  修复后的视频帧 输出文件夹
     * @return 命令
     */
    public static String repairVideoFramesCmd(String rootPath,File tempDir, File outDir) {
        return repair(rootPath,tempDir, outDir, Model.ANIME_VIDEO, MIN_SCALE, ImageFormat.JPG);
    }


    /**
     * 第三步:得到视频 fps
     * ffmpeg -i onepiece_demo.mp4
     * -i                   input video path
     *
     * @param video 视频
     * @return 命令
     */
    public static String getVideoFpsCmd(File video) {
        return MessageFormat.format("ffmpeg -i {0}", video.getAbsolutePath());
    }

    /**
     * 第四步:合并帧
     * ffmpeg -r 23.98 -i out_frames/frame%08d.jpg -c:v libx264 -r 23.98 -pix_fmt yuv420p output.mp4
     * -i                   input video path
     * -c:v                 video encoder (usually we use libx264)
     * -r                   fps, remember to modify it to meet your needs
     * -pix_fmt             pixel format in video
     *
     * @param fps      视频pfs
     * @param outDir   修复后的视频帧保存文件夹
     * @param outVideo 最终输出文件
     * @return 命令
     */
    public static String mergeVideoFramesCmd(String fps, File outDir, File outVideo) {
        return MessageFormat.format("ffmpeg -r {0} -i {1}/frame%08d.jpg -c:v libx264 -r {2} -pix_fmt yuv420p {3}",
                fps, outDir.getAbsolutePath(),
                fps, outVideo.getAbsolutePath()
        );
    }

    /**
     * 第五步:输入视频中复制音频
     * ffmpeg -r 23.98 -i out_frames/frame%08d.jpg -i onepiece_demo.mp4 -map 0:v:0 -map 1:a:0 -c:a copy -c:v libx264 -r 23.98 -pix_fmt yuv420p output_w_audio.mp4
     * Usage:
     * -i                   input video path, here we use two input streams
     * -c:v                 video encoder (usually we use libx264)
     * -r                   fps, remember to modify it to meet your needs
     * -pix_fmt             pixel format in video
     *
     * @param fps      视频pfs
     * @param video    源视频
     * @param outDir   修复后的视频帧保存文件夹
     * @param outVideo 最终输出文件
     * @return 命令
     */
    public static String copyVideoAudio(String fps, File video, File outDir, File outVideo) {
        return MessageFormat.format("ffmpeg -r {0} -i {1}/frame%08d.jpg -i {2} -map 0:v:0 -map 1:a:0 -c:a copy -c:v libx264 -r {3} -pix_fmt yuv420p {4}",
                fps, outVideo.getAbsolutePath(), video.getAbsolutePath(), fps, outDir.getAbsolutePath()
        );
    }


}
