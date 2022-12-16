package com.chansan.extension.dns.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.aliyun.alidns20150109.models.AddDomainRecordRequest;
import com.aliyun.alidns20150109.models.DescribeDomainRecordInfoResponseBody;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.DescribeSubDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.GetTxtRecordForVerifyResponseBody;
import com.aliyun.alidns20150109.models.UpdateDomainRecordRequest;
import com.chansan.extension.dns.DnsService;
import com.chansan.extension.dns.enums.LineStatus;
import com.chansan.extension.dns.enums.RecordStatus;
import com.chansan.extension.dns.enums.RecordType;
import com.chansan.extension.dns.enums.RecordVerifyType;
import com.google.gson.Gson;

/**
 * @name: DnsServiceImplTest
 * @author: leihuangyan
 * @classPath: com.chansan.extension.dns.impl.DnsServiceImplTest
 * @date: 2022/12/16
 * @description:

 */

class DnsServiceImplTest {


//    private final  static  String  DOMAIN_NAME = "seeyou.love";
    private final  static  String  DOMAIN_NAME = "lovefile.cn";

    private final  static  String  RECORD_ID = "801828089609678848";

    private  final  static  DnsService dnsService = new DnsServiceImpl();


    @Test()
    public void getRecord(){

        DescribeDomainRecordsResponseBody record = dnsService.getRecord(DOMAIN_NAME);
        System.out.printf("请求结果%s",record);
        System.out.println("记录:");
        System.out.println(record.getDomainRecords());
        System.out.println("记录列表:");
        System.out.println(record.getDomainRecords().getRecord());

        if(0 == record.getDomainRecords().getRecord().size()){
            System.out.printf("未获取到域名:[%s]解析记录",DOMAIN_NAME);
            System.out.println();
            return;
        }

        record.getDomainRecords().getRecord().forEach(i-> System.out.println(new Gson().toJson(i)));
    }

    @Test
    public void getRecordInfo(){
        DescribeDomainRecordInfoResponseBody body = dnsService.getRecordInfo(RECORD_ID);
        System.out.println(new Gson().toJson(body));
    }

    @Test
    public void getSubRecord(){
        String  subDomainName = "vps.lovefile.cn";
        String  domainName = "";
        DescribeSubDomainRecordsResponseBody body = dnsService.getSubRecord(subDomainName, domainName, RecordType.A);
        System.out.println(new Gson().toJson(body));
    }


    @Test
    public void txtRecordForVerify(){
        GetTxtRecordForVerifyResponseBody body = dnsService.txtRecordForVerify(DOMAIN_NAME, RecordVerifyType.ADD_SUB_DOMAIN);

        System.out.println("域名:"+body.domainName);
        System.out.println("RR:"+body.RR);
        System.out.println("记录值:"+body.value);
        System.out.println("请求ID:"+body.requestId);
    }

    @Test
    public void addRecord(){
        AddDomainRecordRequest request = new AddDomainRecordRequest();
        //域名
        request.domainName = DOMAIN_NAME;
        //主机记录
        request.RR = "vps";
        //解析记录类
        request.type = RecordType.TXT.name();
        //记录值
        request.value = "121.0.0.1";
        //解析生效时间
        request.TTL =600L;
        //解析线路
        request.line ="default";
        Assertions.assertTrue(dnsService.addRecord(request));

    }

    @Test
    public void updateRecord(){
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        //域名
        request.recordId = RECORD_ID;
        //主机记录
        request.RR = "alidnscheck";
        //解析记录类
        request.type = RecordType.TXT.name();
        //记录值
        request.value = "e1316303d7ab4b30b5ffcfd1312647d41";
        //解析生效时间
        request.TTL =610L;
        //解析线路
        request.line = LineStatus.TELECOM.getCode();

        Assertions.assertTrue(dnsService.updateRecord(request));

    }


    @Test
    public void uploadRecordRemark(){
        Assertions.assertTrue(dnsService.uploadRecordRemark(RECORD_ID,"测试修改"));
    }


    @Test
    public void setRecordStatus(){
        Assertions.assertTrue(dnsService.setRecordStatus(RECORD_ID, RecordStatus.ENABLE));
    }


    @Test
    public void delRecord(){
        Assertions.assertTrue(dnsService.delRecord(RECORD_ID));
    }


}