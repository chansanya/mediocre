package com.chansan.extension.runtime.util;

import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import com.chansan.extension.runtime.config.CommandConfig;

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
     * 默认1分钟超时
     * @param command 命令
     */
    public static void run(String command) {
        //默认1分钟超时
        runSync(command, CommandConfig.TimeOut.timeout(60 * 1000));
    }

    /**
     * 运行命令
     * @param command 命令
     * @param timeOut 超时设置
     */
    public static void runSync(String command,CommandConfig.TimeOut timeOut) {
        run(CommandConfig.builder().timeOut(timeOut).async(false).command(command).build());
    }


    /**
     * 异步运行命令 默认不超时
     *
     * @param callBack 成功回调 同步
     * @param command  命令
     */
    public static void runAsync(String command, Consumer<String> callBack) {
        runAsync(command, callBack, null);
    }


    /**
     * 异步运行命令  默认不超时
     * @param command       命令
     * @param callBack      成功回调
     * @param errorCallBack 错误回调
     */
    public static void runAsync(String command, Consumer<String> callBack, Consumer<String> errorCallBack) {
        runAsync(command,callBack,errorCallBack, CommandConfig.TimeOut.none());
    }

    /**
     * 异步运行命令
     *
     * @param command       命令
     * @param timeOut       超时设置
     * @param callBack      成功回调
     * @param errorCallBack 错误回调
     */
    public static void runAsync(String command, Consumer<String> callBack, Consumer<String> errorCallBack, CommandConfig.TimeOut timeOut) {
        run(CommandConfig.builder().timeOut(timeOut).async(true).command(command).callBack(callBack).errorCallBack(errorCallBack).build());
    }


    /**
     * 运行命令
     * @param config 运行时配置
     */
    private static void run(CommandConfig config) {

        CommandLine commandLine = CommandLine.parse(config.getCommand());
        DefaultExecutor exec = new DefaultExecutor();

        try {

            CommandConfig.TimeOut timeOut = config.getTimeOut();
            //是否有设置超时
            if(null != timeOut &&  timeOut.isEnable()){
                //看门狗 ，防止超时
                ExecuteWatchdog watchdog = new ExecuteWatchdog(timeOut.getTimeOut());
                exec.setWatchdog(watchdog);
            }

            //输入输出
            ByteArrayOutputStream susStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(susStream, errStream);
            exec.setStreamHandler(pumpStreamHandler);

            //是否异步
            if (config.isAsync()) {
                exec.execute(commandLine, new CustomExecuteResultHandler(susStream, errStream,config.getCallBack(), config.getErrorCallBack()));

            }else {
                if(exec.isFailure(exec.execute(commandLine))){
                    throw new RuntimeException(MessageFormat.format("执行命令失败:{0}",commandLine));
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    record CustomExecuteResultHandler(ByteArrayOutputStream susStream,
                                      ByteArrayOutputStream errStream,
                                      Consumer<String> callBack,
                                      Consumer<String> errorCallBack
    ) implements ExecuteResultHandler {

        @Override
        public void onProcessComplete(int i) {
            if (null != callBack) {
                //有的消息莫名其妙的跑到errStream里去,此处兼容下
                callBack.accept(Optional.ofNullable(susStream).orElse(errStream).toString());
            }

        }

        @Override
        public void onProcessFailed(ExecuteException e) {
            System.out.println("调用系统程序失败:" + e.getMessage());
            if (null != errorCallBack) {
                errorCallBack.accept(errStream.toString());
            }
        }
    }
}
