package com.chansan.extension.dns.impl;

import java.util.Optional;

import com.aliyun.alidns20150109.Client;
import com.aliyun.alidns20150109.models.AddDomainRecordRequest;
import com.aliyun.alidns20150109.models.AddDomainRecordResponse;
import com.aliyun.alidns20150109.models.DeleteDomainRecordRequest;
import com.aliyun.alidns20150109.models.DeleteDomainRecordResponse;
import com.aliyun.alidns20150109.models.DescribeDomainRecordInfoRequest;
import com.aliyun.alidns20150109.models.DescribeDomainRecordInfoResponse;
import com.aliyun.alidns20150109.models.DescribeDomainRecordInfoResponseBody;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponse;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.DescribeSubDomainRecordsRequest;
import com.aliyun.alidns20150109.models.DescribeSubDomainRecordsResponse;
import com.aliyun.alidns20150109.models.DescribeSubDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.GetTxtRecordForVerifyRequest;
import com.aliyun.alidns20150109.models.GetTxtRecordForVerifyResponse;
import com.aliyun.alidns20150109.models.GetTxtRecordForVerifyResponseBody;
import com.aliyun.alidns20150109.models.SetDomainRecordStatusRequest;
import com.aliyun.alidns20150109.models.SetDomainRecordStatusResponse;
import com.aliyun.alidns20150109.models.UpdateDomainRecordRemarkRequest;
import com.aliyun.alidns20150109.models.UpdateDomainRecordRemarkResponse;
import com.aliyun.alidns20150109.models.UpdateDomainRecordRequest;
import com.aliyun.alidns20150109.models.UpdateDomainRecordResponse;
import com.aliyun.tea.TeaException;
import com.chansan.extension.dns.DnsService;
import com.chansan.extension.dns.enums.RecordStatus;
import com.chansan.extension.dns.enums.RecordType;
import com.chansan.extension.dns.enums.RecordVerifyType;

import lombok.extern.slf4j.Slf4j;

/**
 * @name: DnsServiceImpl
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.impl.DnsServiceImpl
 * @date: 2022/12/16
 * @description:
 */
@Slf4j
public class DnsServiceImpl implements DnsService {


    /**
     * 得到解析记录
     * @param domainName 域名
     * @return 域名解析记录
     */
    @Override
    public DescribeDomainRecordsResponseBody getRecord(String domainName) {

        try {
            //设置请求参数
            DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
            request.domainName = domainName;
            //发起请求
            DescribeDomainRecordsResponse response = new Client(AliDnsConfig.getConfig()).describeDomainRecords(request);
            //验证结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("域名解析记录,无结果"));

            Rep.fail(response.statusCode,"获取域名解析记录");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("域名解析记录,Body为空"));
            //返回
            return response.body;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("获取子域名解析记录未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到解析记录详情
     * @param recordId 记录ID
     * @return 域名解析记录详情
     */
    @Override
    public DescribeDomainRecordInfoResponseBody getRecordInfo(String recordId) {
        try {
            //设置请求参数
            DescribeDomainRecordInfoRequest request = new DescribeDomainRecordInfoRequest();
            request.recordId = recordId;
            //发起请求
            DescribeDomainRecordInfoResponse response = new Client(AliDnsConfig.getConfig()).describeDomainRecordInfo(request);
            //验证结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("域名解析详情,无结果"));
            Rep.fail(response.statusCode,"域名解析详情");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("域名解析详情,Body为空"));
            //返回
            return response.body;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("获取子域名解析记录未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到解析记录
     *
     * @param subDomain 子域名，以参数subDomain为a.www.example.com为示例：
     *                      如果参数domainName为空，则查询域名为example.com、主机名为”a.www“的解析记录。
     *                      如果参数domainName为 www.example.com，则查询域名为www.example.com、主机名为 "a"的解析记录。
     *                      如果参数domainName为 a.www.example.com，则查询域名为a.www.example.com、主机名为 "@"的解析记录。
     * @param domainName    域名
     * @param recordType    记录类型
     * @return 域名解析记录
     */
    @Override
    public DescribeSubDomainRecordsResponseBody getSubRecord(String subDomain, String domainName, RecordType recordType) {
        try {
            //设置请求参数
            DescribeSubDomainRecordsRequest request = new DescribeSubDomainRecordsRequest();
            request.subDomain = subDomain;
            request.domainName = domainName;
            request.type = recordType.name();
            //发起请求
            DescribeSubDomainRecordsResponse response = new Client(AliDnsConfig.getConfig()).describeSubDomainRecords(request);
            //验证结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("子域名解析记录,无结果"));
            Rep.fail(response.statusCode,"子域名解析记录");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("子域名解析记录,Body为空"));
            //返回
            return response.body;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("获取子域名解析记录未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置记录状态
     *
     * @param recordId 记录ID
     * @param status   状态
     * @return 是否成功
     */
    @Override
    public boolean setRecordStatus(String recordId, RecordStatus status) {
        try {
            //设置参数
            SetDomainRecordStatusRequest request = new SetDomainRecordStatusRequest();
            request.recordId = recordId;
            request.status = status.getStatus();
            //开始请求
            SetDomainRecordStatusResponse response = new Client(AliDnsConfig.getConfig()).setDomainRecordStatus(request);
            //处理结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("设置记录状态,无结果"));
            Rep.fail(response.statusCode,"设置记录状态");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("设置记录状态,Body为空"));
            //返回
            return Boolean.TRUE;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("设置记录状态未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置记录备注
     *
     * @param recordId 记录ID
     * @param remark   备注
     * @return 是否成功
     */
    @Override
    public boolean uploadRecordRemark(String recordId, String remark) {
        try {
            //设置参数
            UpdateDomainRecordRemarkRequest request = new UpdateDomainRecordRemarkRequest();
            request.recordId = recordId;
            request.remark = remark;
            //开始请求
            UpdateDomainRecordRemarkResponse response = new Client(AliDnsConfig.getConfig()).updateDomainRecordRemark(request);
            //处理结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("修改记录备注,无结果"));
            Rep.fail(response.statusCode,"修改记录备注");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("修改记录备注,Body为空"));
            //返回
            return Boolean.TRUE;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("修改记录备注未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置记录备注
     *
     * @param request  新增解析记录参数
     * @return 是否成功
     */
    @Override
    public boolean addRecord(AddDomainRecordRequest request) {
        try {
            //开始请求
            AddDomainRecordResponse response = new Client(AliDnsConfig.getConfig()).addDomainRecord(request);
            //处理结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("新增解析记录,无结果"));
            Rep.fail(response.statusCode,"新增解析记录");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("新增解析记录,Body为空"));
            //返回
            return Boolean.TRUE;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("新增解析记录未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改解析激励
     *
     * @param request 修改解析记录参数
     * @return 是否成功
     */
    @Override
    public boolean updateRecord(UpdateDomainRecordRequest request) {
        try {
            //开始请求
            UpdateDomainRecordResponse response = new Client(AliDnsConfig.getConfig()).updateDomainRecord(request);
            //处理结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("新增解析记录,无结果"));
            Rep.fail(response.statusCode,"新增解析记录");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("新增解析记录,Body为空"));
            //返回
            return Boolean.TRUE;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("新增解析记录未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除解析记录
     * @param recordId 记录ID
     * @return 是否成功
     */
    @Override
    public boolean delRecord(String recordId) {
        try {
            //设置参数
            DeleteDomainRecordRequest request = new DeleteDomainRecordRequest();
            //域名
            request.recordId = recordId;

            //开始请求
            DeleteDomainRecordResponse response = new Client(AliDnsConfig.getConfig()).deleteDomainRecord(request);
            //处理结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("删除解析记录,无结果"));
            Rep.fail(response.statusCode,"删除解析记录");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("删除解析记录,Body为空"));
            //返回
            return Boolean.TRUE;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("删除解析记录未知异常",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成TXT记录
     * @param domainName 域名
     * @param recordVerifyType txt验证的功能类型
     * @return 是否成功
     */
    @Override
    public GetTxtRecordForVerifyResponseBody txtRecordForVerify(String domainName, RecordVerifyType recordVerifyType) {
        try {
            //设置参数
            GetTxtRecordForVerifyRequest request = new GetTxtRecordForVerifyRequest();
            //域名
            request.domainName = domainName;
            request.type = recordVerifyType.name();

            //开始请求
            GetTxtRecordForVerifyResponse response = new Client(AliDnsConfig.getConfig()).getTxtRecordForVerify(request);
            //处理结果
            Optional.ofNullable(response).orElseThrow(()->new RuntimeException("生成TXT记录,无结果"));
            Rep.fail(response.statusCode,"生成TXT记录");
            Optional.ofNullable(response.body).orElseThrow(()->new RuntimeException("生成TXT记录,Body为空"));
            //返回
            return response.body;
        } catch (Exception e) {
            if(e instanceof TeaException exception){
                throw new RuntimeException(exception.message);
            }
            log.error("生成TXT记录未知异常",e);
            throw new RuntimeException(e);
        }
    }



}
