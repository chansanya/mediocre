package com.chansan.extension.gen.impl;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.chansan.extension.gen.GenService;
import com.chansan.extension.gen.model.FrpClientModel;
import com.chansan.extension.gen.model.FrpServerModel;

/**
 * @name: GenFrpClientConfigServiceImplTest
 * @author: leihuangyan
 * @classPath: com.chansan.extension.gen.impl.GenFrpClientConfigServiceImplTest
 * @date: 2022/12/16
 * @description:
 */
class GenFrpClientConfigServiceImplTest {

    GenService genService = new GenFrpClientConfigServiceImpl();


    @Test
    public void  genFrpClient(){
        List<FrpClientModel.Service> services = List.of(
                FrpClientModel.Service.builder().name("vps").type("HTTP").localPort(8080).domain("vps.test.cn").build(),
                FrpClientModel.Service.builder().name("frp").type("HTTP").localPort(8081).domain("frp.test.cn").build()
        );


        FrpClientModel build =new  FrpClientModel()
                .setServerAddr("127.0.0.1")
                .setVisitPort(7301)
                .setToken("12345678")
                .setServerPort(7000)
                .setServices(services);

        genService.gen(build);
    }

    @Test
    public void  genFrpServer(){

        FrpServerModel build =new FrpServerModel()
                .setBindPort(7000)
                .setVisitPort(7001)
                .setToken("12345678")
                .setDashboardPort(7500)
                .setDashboardUser("admin")
                .setDashboardPwd("services");

        genService.gen(build);
    }

}