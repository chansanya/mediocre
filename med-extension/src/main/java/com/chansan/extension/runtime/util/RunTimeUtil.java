package com.chansan.extension.runtime.util;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * @name: RunTimeUtil
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.util.RunTimeUtil
 * @date: 2022/12/17
 * @description: 运行
 */
@SuppressWarnings("unused")
public class RunTimeUtil {


    /**
     * 运行命令
     * @param command 命令
     */
    public static void run(String command){
        run(command,false);
    }

    /**
     * 异步运行命令
     * @param command 命令
     */
    public static void runAsync(String command){
        run(command,true);
    }

    /**
     * 运行命令
     * @param command 命令
     * @param async 是否异步
     */
    public static void run(String command,Boolean async){
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();

        try {
            //看门狗 ，防止超时
            ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
            exec.setWatchdog(watchdog);

            //输入输出
            ByteArrayOutputStream susStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(susStream,errStream);
            exec.setStreamHandler(pumpStreamHandler);

            if(async){
                System.out.println("异步执行开始");
                exec.execute(commandLine, new CustomExecuteResultHandler(susStream,errStream));
                return;
            }

            exec.execute(commandLine);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


   record  CustomExecuteResultHandler(ByteArrayOutputStream susStream ,ByteArrayOutputStream errStream ) implements ExecuteResultHandler {

        @Override
        public void onProcessComplete(int i) {

            System.out.println("执行成功,状态码:"+i);
            System.out.println("成功:>>"+susStream);
            System.out.println("错误:>>"+errStream);

        }

        @Override
        public void onProcessFailed(ExecuteException e) {
            System.out.println("调用系统程序失败:{}"+errStream+"==="+e.getMessage());
        }
    }
}
