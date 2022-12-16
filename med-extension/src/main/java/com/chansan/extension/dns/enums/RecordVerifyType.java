package com.chansan.extension.dns.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name: RecordStatus
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.enums.RecordStatus
 * @date: 2022/12/16
 * @description: txt验证的功能
 */
@AllArgsConstructor
@Getter
public enum RecordVerifyType {
    /**
     *
     */
    ADD_SUB_DOMAIN,
    /**
     *
     */
    RETRIEVAL


}
