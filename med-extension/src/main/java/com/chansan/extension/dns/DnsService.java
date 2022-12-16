package com.chansan.extension.dns;

import com.aliyun.alidns20150109.models.AddDomainRecordRequest;
import com.aliyun.alidns20150109.models.DescribeDomainRecordInfoResponseBody;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.DescribeSubDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.GetTxtRecordForVerifyResponseBody;
import com.aliyun.alidns20150109.models.UpdateDomainRecordRequest;
import com.chansan.extension.dns.enums.RecordStatus;
import com.chansan.extension.dns.enums.RecordType;
import com.chansan.extension.dns.enums.RecordVerifyType;

/**
 * @name: DnsService
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.DnsService
 * @date: 2022/12/16
 * @description:
 */

public interface DnsService {


    /**
     * 得到解析记录
     * @param domainName 域名
     * @return 域名解析记录
     */
    DescribeDomainRecordsResponseBody getRecord(String domainName);

    /**
     * 得到解析记录
     * @param subDomainName 子域名
     * @param domainName 域名
     * @param recordType 记录类型
     * @return 域名解析记录
     */
    DescribeSubDomainRecordsResponseBody getSubRecord(String subDomainName, String domainName, RecordType recordType);

    /**
     * 得到解析记录详情
     * @param recordId 记录ID
     * @return 域名解析记录详情
     */
    DescribeDomainRecordInfoResponseBody getRecordInfo(String recordId);

    /**
     * 设置记录状态
     * @param recordId 记录ID
     * @param status 状态
     * @return 是否成功
     */
    boolean setRecordStatus(String recordId, RecordStatus status);

    /**
     * 设置记录备注
     * @param recordId 记录ID
     * @param remark 备注
     * @return 是否成功
     */
    boolean uploadRecordRemark(String recordId,String remark);


    /**
     * 设置记录备注
     * @param request 新增解析记录参数
     * @return 是否成功
     */
    boolean addRecord(AddDomainRecordRequest request);

    /**
     * 修改解析激励
     * @param request 修改解析记录参数
     * @return 是否成功
     */
    boolean updateRecord(UpdateDomainRecordRequest request);

    /**
     * 删除解析记录
     * @param recordId 记录ID
     * @return 是否成功
     */
    boolean delRecord(String recordId);

    /**
     * 生成TXT记录
     * @param domainName 域名
     * @param recordVerifyType txt验证的功能类型
     * @return 生成的TXT信息
     */
    GetTxtRecordForVerifyResponseBody txtRecordForVerify(String domainName, RecordVerifyType recordVerifyType);

}
