# mediocre-admin

> 学习使我快乐

### 基础环境
* JDK17
* Gradle 7.5.1
* SpringBoot 3.0

### 当前引入组件
* [mybatis-plus](https://baomidou.com/pages/24112f/)
* [spring-boot-starter-actuator](https://docs.spring.io/spring-boot/docs/3.0.0/actuator-api/htmlsingle/)
* [hutool](https://hutool.cn/)

### JDK 17注意事项
* **使用module-info时,必须引入模块名**
  * 对模块不了解就不要用啊
* **Resource 包名变更** 
  * javax.annotation.Resource >>> jakarta.annotation.Resource [详情](https://jiagoushi.pro/book/export/html/579)

> 持续集成中。。。