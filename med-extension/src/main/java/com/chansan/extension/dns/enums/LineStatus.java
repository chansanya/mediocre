package com.chansan.extension.dns.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name: RecordStatus
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.enums.RecordStatus
 * @date: 2022/12/16
 * @description: 线路
 * 参考
 * <a href="https://help.aliyun.com/document_detail/29807.html?spm=api-workbench.API%20Explorer.0.0.96b71e0fsuQXg8">...</a>
 */
@AllArgsConstructor
@Getter
public enum LineStatus {
    /**
     */
    DEFAULT("default","默认"),
    TELECOM("telecom","电信"),
    UNICOM("unicom","联通"),
    MOBILE("mobile","移动"),
    OVERSEA("oversea","海外"),
    EDU("edu","教育网"),
    DRPENG("drpeng","鹏博士"),
    BTVN("btvn","广电网"),

    ;
    private final String code;
    private final String lineName;
}
