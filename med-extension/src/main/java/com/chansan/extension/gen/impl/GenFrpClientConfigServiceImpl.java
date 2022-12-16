package com.chansan.extension.gen.impl;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.chansan.extension.gen.GenService;
import com.chansan.extension.gen.model.BaseModel;
import com.chansan.extension.gen.util.ReflectUtil;
import com.chansan.extension.gen.util.VelocityInitializer;

/**
 * @name: GenServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.extension.gen.impl.GenServiceImpl
 * @date: 2022/12/16
 * @description:
 */
public class GenFrpClientConfigServiceImpl implements GenService {

    /**
     * 构建
     *
     * @param model 通用构建模型，详情查看具体实现
     * @return 基于模板生成的数据
     */
    @Override
    public String gen(BaseModel model) {

        VelocityInitializer.initVelocity();
        VelocityContext context = new VelocityContext();

        ReflectUtil.setContext(context,model);

        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(model.getTemplatePath(), StandardCharsets.UTF_8.name());
        tpl.merge(context, sw);
        return sw.toString();
    }


}
