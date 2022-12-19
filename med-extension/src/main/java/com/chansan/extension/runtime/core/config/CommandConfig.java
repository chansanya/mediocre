package com.chansan.extension.runtime.core.config;

import java.util.function.Consumer;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @name: CommandConfig
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.core.config.CommandConfig
 * @date: 2022/12/19
 * @description:
 */
@Data
@Accessors(chain = true)
@Builder
public class CommandConfig {

    /**
     * 执行的命令
     */
    private String command;
    /**
     * 是否异步
     */
    private boolean async;
    /**
     * 成功回调
     */
    private Consumer<String> callBack;

    /**
     * 失败回调
     */
    private Consumer<String> errorCallBack;
    /**
     * 超时设置
     */
    private TimeOut timeOut;

    /**
     * 是否异步
     */
    @Getter
    public static class TimeOut {

        /**
         * 是否开启
         */
        private boolean enable;

        /**
         * 单位毫秒
         */
        private long timeOut;

        private TimeOut(boolean enable, long timeOut) {
            this.enable = enable;
            this.timeOut = timeOut;
        }


        /**
         * 不超时
         */
        public static TimeOut none(){
            return new TimeOut(false,0);
        }

        public static TimeOut timeout(long timeout){
            return new TimeOut(true,timeout);
        }

    }
}
