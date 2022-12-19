package com.chansan.extension.runtime.process.base;

import java.util.Optional;
import java.util.function.Consumer;

import com.chansan.extension.runtime.util.RunTimeUtil;

/**
 * @name: AbstractFileProcess
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.process.AbstractFileProcess
 * @date: 2022/12/19
 * @description: 链式处理器
 */
public abstract  class AbstractChainProcess {
    /**
     * 下一道程序
     */
    private AbstractChainProcess next;
    /**
     * 当前命令
     */
    private String cmd;
    /**
     * 是否异步
     */
    private boolean async;

    public AbstractChainProcess(AbstractChainProcess next, String cmd) {
        this.next = next;
        this.cmd = cmd;
        this.async = true;
    }

    /**
     * 执行
     * 此方法子类不可重写
     */
    public final void  exec(){
        run(this::success, this::error);
    }

    /**
     * 运行，
     * @param callBack 成功回调
     * @param errorCallBack 失败回调
     */
    private  void run(Consumer<String> callBack, Consumer<String> errorCallBack){
        System.out.println("当前命令:"+cmd);
        if(async){
            RunTimeUtil.runAsync(cmd, callBack,errorCallBack);
        }else {
            RunTimeUtil.run(cmd);
        }
    }

    /**
     * 程序执行成功处理
     * @param result 执行成功消息
     */
    protected  void success(String result){
        handelCmd(result);
        Optional.ofNullable(next).ifPresent(AbstractChainProcess::exec);
    };

    /**
     * 程序错误时处理
     * @param error 程序打印的错误消息
     */
    protected  void error(String error){
        System.out.println("程序执行失败:"+ error);
    };

    /**
     * 处理当前命令， 如果当前命令需要根据上一个命令结果变更时，可以重写此方法
     * @param result 上一个程序执行结果
     */
    protected  void handelCmd(String result){

    };

    /**
     * 设置下一个程序
     * @param next 下一个需要执行的程序
     */
    public void setNext(AbstractChainProcess next) {
        this.next = next;
    }


    /**
     * 得到命令 子类才能使用
     * @return cmd
     */
    protected String getCmd() {
        return cmd;
    }


    /**
     * 设置命令 子类才能使用
     * @param  cmd 设置cmd会覆盖原有设置
     */
    protected void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * 设置异步 exec前执行生效
     * @param async 异步
     */
    public void setAsync(boolean async) {
        this.async = async;
    }
}
