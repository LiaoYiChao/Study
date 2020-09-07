## SpringCloud 构建

### SpringCloud OpenFeign

- 概述：[OpenFeign](https://blog.csdn.net/taiyangdao/article/details/81359394)

##### OpenFeign 重要注解

- @FeignClient 和 @EnableFeignClients
    - @FeignClient 注解用于声明 Feign 客户端可以访问的 Web 服务.
        - 其中内部参数：
            - name , value , qualifier，url (默认 “”) ，两者等价
            - decode404（默认 false）
            - configuration（默认 FeignClientsConfiguration.class）
                - configuration 参数默认是 FeignClientsConfiguration
            - fallback（默认 void.class）
            - path（默认 ""）
            - primary（默认 true）
    - @EnableFeignClients 用于修饰 SpringBoot 应用的入口类，以通知 SpringBoot 启动应用时，扫描应用中声明的 Feign 客户端可访问的 Web 服务.
    - 