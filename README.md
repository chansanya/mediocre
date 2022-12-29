# mediocre-admin

> 学习使我快乐

### 基础环境
* JDK17
* Gradle 7.5.1
* SpringBoot 3.0

### 当前引入组件
* [mybatis-plus](https://baomidou.com/pages/24112f/),默认数据源 hikari (2022/12/15 druid 还不支持Springboot3.0)
* [spring-boot-starter-actuator](https://docs.spring.io/spring-boot/docs/3.0.0/actuator-api/htmlsingle/)
* [spring-boot-starter-security](https://docs.spring.io/spring-security/reference/whats-new.html)
* [hutool](https://hutool.cn/)
* [velocity](https://velocity.apache.org/)
* [ap](https://velocity.apache.org/)
* 

### 注意事项
* **JDK**
  * **使用module-info时,必须引入模块名**
    * 对模块不了解就不要用啊
  * **Resource 包名变更** 
    * javax.annotation.Resource >>> jakarta.annotation.Resource [详情](https://jiagoushi.pro/book/export/html/579)
* **security**
  * WebSecurityConfigurerAdapter 移除,[参考](https://docs.spring.io/spring-security/reference/whats-new.html)
> 持续集成中。。。

- [数据结构](https://github.com/chansanya/mediocre/blob/master/med-extension/src/main/java/com/chansan/extension/study/%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84.md)