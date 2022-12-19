package com.chansan.extension.runtime.core.process.base;

/**
 * @name: AbstractFileProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.core.process.AbstractFileProcess
 * @date: 2022/12/19
 * @description: 单个处理器
 */
public abstract  class AbstractSingleProcess extends AbstractChainProcess{

    public AbstractSingleProcess(String cmd) {
        super(null,cmd);
    }


    /**
     * 单条命令 不允许设置下个节点  因此此处为 final修饰
     * @param next 下一个需要执行的程序
     */
    @Override
    public final void setNext(AbstractChainProcess next) {
    }

}
