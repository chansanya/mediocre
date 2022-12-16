package com.chansan.extension.dns.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name: RecordStatus
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.enums.RecordStatus
 * @date: 2022/12/16
 * @description: 解析记录状态
 */
@AllArgsConstructor
@Getter
public enum RecordStatus {
    /**
     * Enable: 启用解析
     * Disable: 暂停解析
     */
    ENABLE("Enable"),
    DISABLE("Disable")
    ;
    private final String status;
}
