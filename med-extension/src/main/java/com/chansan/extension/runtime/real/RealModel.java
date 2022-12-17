package com.chansan.extension.runtime.real;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.chansan.extension.runtime.real.enums.Model;
import com.chansan.extension.runtime.real.util.CmdGenUtil;

import lombok.Builder;
import lombok.Data;

/**
 * @name: RealModel
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.RealModel
 * @date: 2022/12/17
 * @description: 模型
 * <a href="https://github.com/xinntao/Real-ESRGAN/blob/master/README_CN.md">...</a>
 * 模型介绍
 * <a href="https://github.com/xinntao/Real-ESRGAN/blob/master/docs/anime_video_model.md">...</a>
 * 比较
 * <a href="https://github.com/xinntao/Real-ESRGAN/blob/master/docs/anime_comparisons_CN.md">...</a>
 */
@Data
@Builder
public class RealModel {

    private String rootPath;
    private File source;
    private File target;
    private Model model;
    private boolean async;

    public String getImgCmd(){
        if (null == source || source.isDirectory()){
            throw new RuntimeException("源文件不存在");
        }
        if (null == target || target.isDirectory()){
            throw new RuntimeException("目标文件不存在");
        }
        if(StringUtils.isBlank(rootPath)){
            throw new RuntimeException("执行文件根目录不能为空");
        }
        System.out.println("源文件:"+source);
        System.out.println("目标文件:"+target);
        System.out.println("模型:"+model);
        return CmdGenUtil.repairImg(rootPath,source,target,model);
    }

}
