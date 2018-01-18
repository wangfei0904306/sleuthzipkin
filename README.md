# sleuth与zipkin配合进行服务追踪
----
## 依次启动项目中所有服务
* EurekaApplication
* ZuulApplication
* ZipkinApplication
* InvokerApplication
* ExampleService2Application
* ExampleServiceApplication
* 
## 浏览器打开以下网址
* [http://localhost:10007/invoker/get](http://localhost:10007/invoker/get)
* [http://localhost:10002/example-service/example/get](http://localhost:10002/example-service/example/get)
* [http://localhost:10002/invoker/invoker/get](http://localhost:10002/invoker/invoker/get)

## 查看结果
网页地址：[http://localhost:9411/zipkin](http://localhost:9411/zipkin)
打开网页后选择时间（范围一定够大），Limit改为1000，然后点击**Find Trances**
