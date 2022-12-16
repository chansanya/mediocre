package com.chansan.extension.dns.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name: RecordStatus
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.enums.RecordStatus
 * @date: 2022/12/16
 * @description: 主机记录类型
 *
 * 参考
 *  {@link <a href="https://help.aliyun.com/document_detail/29805.html?spm=api-workbench.API%20Explorer.0.0.13a61e0frKBchf">...</a>}
 */
@AllArgsConstructor
@Getter
public enum RecordType {
    /**
     *
     * A记录	A
     * 参考标准；RR值可为空，即@解析；不允许含有下划线；
     * IPv4地址格式
     */
    A,
    /**
     * NS记录	NS
     * 参考标准；RR值不能为空；允许含有下划线；不支持泛解析
     * NameType形式
     */
    NS,
    /**
     *  MX记录	MX
     *  参考标准；RR值可为空，即@解析；不允许含有下划线	NameType形式，且不可为IP地址。1-10，优先级依次递减。
     */
    MX,
    /**
     * TXT记录	TXT
     * 参考标准；另外，有效字符除字母、数字、“-”（中横杠）、还包括“_”（下划线）；RR值可为空，即@解析；允许含有下划线；不支持泛解析	字符串；长度小于512,合法字符：大小写字母，数字,空格，及以下字符：-~=:;/.@+^!*
     */
    TXT,
    /**
     * CNAME记录	CNAME	参考标准；另外，有效字符除字母、数字、“-”（中横杠）、还包括“_”（下划线）；RR值不允许为空（即@）；允许含有下划线	NameType形式，且不可为IP
     *
     */
    CNAME,
    /**
     *SRV记录	SRV	是一个name，且可含有下划线“_“和点“.”；允许含有下划线；可为空（即@）；不支持泛解析
     *  priority：优先级，为0－65535之间的数字；weight：权重，为0－65535之间的数字；port：提供服务的端口号，
     *  为0－65535之间的数字 target：为提供服务的目标地址，为nameType，且存在。参考：
     *  <a href="https://en.wikipedia.org/wiki/SRV_record">...</a>
     *  <a href="http://www.rfc-editor.org/rfc/rfc2782.txt">...</a>
     */
    SRV,
    /**
     *AAAA记录	AAAA	参考标准；RR值可为空，即@解析；不允许含有下划线；	IPv6地址格式
     */
    AAAA,
    /**
     *CAA记录	CAA	参考标准；RR值可为空，即@解析；不允许含有下划线；	格式为：[flag] [tag] [value]，是由一个标志字节的[flag],和一个被称为属性的标签[tag]-值[value]对组成。例如：@ 0 issue "symantec.com"或@ 0 iodef "mailto:admin@aliyun.com"
     */
    CAA,
    /**
     *显性URL转发	REDIRECT_URL	参考标准；RR值可为空，即@解析	NameType或URL地址（区分大小写），长度最长为500字符，其中域名，如example.com，必须，大小写不敏感；协议：可选，如HTTP、HTTPS，默认为HTTP端口：可选，如81，默认为80；路径：可选，大小写敏感，如/path/to/，默认为/；文件名：可选，大小写敏感，如file.php，默认无；参数：可选，大小写敏感，如?user=my***，默认无。
     */
    REDIRECT_URL,
    /**
     *隐性URL转发	FORWARD_URL	参考标准；RR值可为空，即@解析	NameType或URL地址（区分大小写），长度最长为500字符，其中域名，如example.com，必须，大小写不敏感；协议：可选，如HTTP、HTTPS，默认为HTTP端口：可选，如81，默认为80；路径：可选，大小写敏感，如/path/to/，默认为/；文件名：可选，大小写敏感，如file.php，默认无；参数：可选，大小写敏感，如?user=my***，默认无。
     */
    FORWARD_URL,


}
