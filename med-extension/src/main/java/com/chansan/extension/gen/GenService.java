package com.chansan.extension.gen;

import com.chansan.extension.gen.model.BaseModel;

/**
 * @name: GenService
 * @author: leihuangyan
 * @classPath: com.chansan.extension.gen.GenService
 * @date: 2022/12/16
 * @description:
 */
public interface GenService {


    /**
     * 构建
     * @param model 通用构建模型，详情查看具体实现
     * @return 基于模板生成的数据
     */
    String gen(BaseModel model);

}
