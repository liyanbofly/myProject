

一、spring boot controller接收和响应实体是用MappingJackson2HttpMessageConverter 转换的
Spring Boot 中的 Controller 在将响应实体转换为 JSON 格式时，默认使用的是 `MappingJackson2HttpMessageConverter`。
`MappingJackson2HttpMessageConverter` 是 Spring 框架提供的一个 HTTP 消息转换器，用于将 Java 对象转换为 JSON 格式的响应体。它使用 Jackson 库来进行 JSON 的序列化和反序列化操作。
当你在 Spring Boot 中编写一个 Controller 方法，并使用 `@ResponseBody` 注解或者在类上使用 `@RestController` 注解时，Spring Boot 会自动将方法返回的对象转换为 JSON 格式的响应体。
总结起来，Spring Boot 中的 Controller 在将响应实体转换为 JSON 格式时，默认使用的是 `MappingJackson2HttpMessageConverter`。它使用 Jackson 库来进行 JSON 的序列化和反序列化操作。

二、HttpConvert 转换日期类型格式












--------------------待制作-----------
1、登录拦截器
2、全局异常处理



______________kafka_________________

1、启动kafka
https://blog.csdn.net/m0_37591671/article/details/136655632
  目录：D:\soft\kafka_2.12-3.5.2\bin
 1)在此目录下打开cmd，执行命令
   zookeeper-server-start.bat ..\..\config\zookeeper.properties
  2) 在此目录下打开cmd，执行命令
     kafka-server-start.bat ..\..\config\server.properties


2、topic 创建
到kafka_2.12-3.5.2\bin\windows 目录下cmd
D:\soft\kafka_2.12-3.5.2\bin\windows>kafka-console-producer.bat --broker-list localhost:9092 --topic test

3、序列化和反充列化 String、Long、对象-》json 类似
 StringSerializer： 将字符串转化为字节数组
 StringDeserializer： 将字节数组转化为字符串


 -------rocket mq----------
 1、
 参考：https://blog.csdn.net/qq_35701988/article/details/141827003
 但启动 mqbroker 报错，参考： https://blog.csdn.net/qq_44981526/article/details/135819403
 解决broker不能正常启动问题：https://cloud.tencent.com/developer/article/2476537
 rokcet mq日志在：C:\Users\Administrator\logs\rocketmqlogs

 2、启动rocketmq-console 查看 tpic、broker情况
 D:\WorkAll\OtherProject\rocketmq-dashboard   target 下 jar

 3、启动
 start mqnamesrv
 start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true

 3、待研究
 producer.start(); 重复start是否有问题
 生产者和消费者 各种场景测试和验证


