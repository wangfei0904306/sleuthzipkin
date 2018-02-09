# sleuth与zipkin配合进行服务追踪
----
## 项目使用
#### 依次启动项目中所有服务
* **EurekaApplication**  注册中心
* **ZuulApplication**  Zuul网关调用
* **ZipkinApplication**  zipkin服务器
* **InvokerApplication**  RestTemplate调用
* **ExampleService2Application**  被调用的服务
* **ExampleServiceApplication**  被调用的服务

#### 浏览器打开以下网址
* [http://localhost:10007/invoker/get](http://localhost:10007/invoker/get)
* [http://localhost:10002/example-service/example/get](http://localhost:10002/example-service/example/get)
* [http://localhost:10002/invoker/invoker/get](http://localhost:10002/invoker/invoker/get)

#### 查看结果
网页地址：[http://localhost:9411/zipkin/](http://localhost:9411/zipkin/)
打开网页后选择时间（范围一定够大），Limit改为1000，然后点击**Find Trances**
## 注意事项
1. 打开日志便于观察
```
logging:
  level:
    root: INFO
    org.springframework.web.servlet.DispatcherServlet: DEBUG
    org.springframework.cloud.sleuth: DEBUG
```
2. 更改抽样比率
```
spring:
  sleuth:
    sampler:
      percentage: 1.0
```
