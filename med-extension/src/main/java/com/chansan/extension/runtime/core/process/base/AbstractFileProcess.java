package com.chansan.extension.runtime.core.process.base;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.chansan.extension.exception.RealException;

/**
 * @name: AbstractFileProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.core.process.AbstractFileProcess
 * @date: 2022/12/19
 * @description: 文件处理器
 */
public abstract  class AbstractFileProcess extends AbstractChainProcess{

    public AbstractFileProcess(AbstractChainProcess next, String cmd) {
        super(next,cmd);
    }


    @SuppressWarnings("all")
    public void  delFiles(File... files){
        if(null == files || 0 == files.length){
            return;
        }
        Arrays.asList(files).forEach(this::delFile);
    }

    public void  delFile(File  file){
        try {
            if (file.isDirectory()){
                FileUtils.deleteDirectory(file);
                System.out.printf("删除文件夹：%s",file);
                return;
            }
            File deleteFile = FileUtils.delete(file);
            System.out.printf("删除文件：%s",deleteFile);
        } catch (IOException e) {
            throw new RealException("删除文件或文件夹失败");
        }
    }

}
