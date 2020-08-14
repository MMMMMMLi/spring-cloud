## 说明

### Spring-Cloud-Feign

它基于Netflix Feign实现，整合了Spring Cloud Ribbon 和 Spring Cloud Hystrix，除了提供这两者的强大功能外，它还提供了一种声明式的Web服务客户端定义方式。

Spring Cloud Feign在Ribbon基础上做了封装，只需要创建一个接口并用注解的方式配置它，就可以完成对服务提供方的接口绑定，简化了在使用Ribbon时自行封装服务调用客户端的开发量。

Spring Cloud Feign具备可插拔的注解支持，包括Feign注解和JAX-RS注解。

同时，它在Netflix Feign的基础上扩展了对Spring MVC注解的支持，这对于习惯使用Spring MVC的开发者来说大大减少了学习成本。

另外，Feign自身的一些组件如编码器和解码器等，也是以插拔的方式提供，在有需求的时候我们可以方便的扩展和替换它们。

- Spring Cloud Ribbon :
  
  Ribbon是一个负载均衡组件，具有丰富的负载均衡策略、重试机制、支持多协议的异步与响应式模型、容错、缓存与批处理功能。
  
  Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，可以让我们轻松地将面向服务的REST模版请求自动转换成客户端负载均衡的服务调用。
  
  Spring Cloud Ribbon虽然只是一个工具类框架，它不像服务注册中心、配置中心、API网关那样需要独立部署，但是它几乎存在于每一个Spring Cloud构建的微服务和基础设施中。
  
  因为微服务间的调用，API网关的请求转发等内容，实际上都是通过Ribbon来实现的。
  
- Spring Cloud Hystrix :
  
  在微服务架构中通常会有多个服务层调用，基础服务的故障可能会导致级联故障，进而造成整个系统不可用的情况，这种现象被称为服务雪崩效应。服务雪崩效应是一种因 “服务提供者” 的不可用导致 “服务消费者” 的不可用, 并将不可用逐渐放大的过程。
    
  Hystrix 会在某个服务连续调用 N 次不响应的情况下，立即通知调用端调用失败，避免调用端持续等待而影响了整体服务。Hystrix 间隔时间会再次检查此服务，如果服务恢复将继续提供服务。

### 服务提供者

服务注册：
```shell script
服务提供者在启动后会通过REST请求的方式将自己注册到服务注册中心，并附带自身的一些元数据,
服务注册中心接收到这些元数据后会把它们存储到一个双层结构Map中，第一层的key值是服务名，第二层是具体服务的实例名

在服务注册时注意参数：
  eureka.client,register-with-eureka=true 如果是false不会注册。
```

服务同步：
```shell script
如果具有多个服务注册中心，其中一个服务中心接受到服务提供者的注册信息后会转发给其他的注册中心，所以服务列表是同步的，可以通过任意一个服务注册中心获取服务列表。
```

服务续约：
```shell script
服务注册后，服务提供者会维护一个心跳来告诉服务注册中心自己还在正常运行，以避免服务注册中心将自己在服务列表剔除。
两个重要的属性

eureka.instance.lease-expiration-duration-in-seconds=90 # 定义服务失效的时间，默认90s
eureka.instance. lease-renewal-interval-in-seconds=30 # 定义服务续约任务的调用时间，默认30s
```

### 服务消费者

获取服务：
```shell script
启动服务消费者时，会自动发送REST请求去服务注册中心获取注册的服务清单,
为了性能考虑，Eureka Server会返回一份可读的服务清单给服务消费者，并且每30s更新一次缓存清单。

必须确保参数：
  eureka.client.fetch-registry=true
如果被修改成false,否则无法在服务注册中心获取服务清单，若想修改缓存清单的更新时间可以通过：
  eureka.client.registry-fetch-interval-seconds=30 # 该参数默认30s.
```

服务调用：
```shell script
服务消费者获取服务清单后，通过服务名可以获得具体提供服务的实例名和该实例的元数据信息，根据这些元数据信息，服务消费者可以自己决定调用哪个服务实例，Ribbon默认是采用轮询的方式进行客户端负载均衡。
对于访问实例的选择，简单介绍一下。
Eureka中有Region和Zone的概念，一个Region可以包含多个Zone，每个客户端都会被注册到一个Zone中，所以每个客户端都对应一个Region和一个Zone，
在进行服务调用的时候会优先选择同处于一个Zone中的服务提供者，若访问不到才会访问其他Zone。
通过Zone属性的由来，配合实际部署的物理结构，可以有效的设计出对区域性故障的容错集群。
```

服务下线：
```shell script
当客户端下线或重启时会给Eureka Server发送REST请求告诉服务端它下线了，服务端接到请求会将该服务改为下线状态（DOWN），并传播出去。
```

## 代码环境说明

### 准备

需要构建好`Eureka-Server` 端，构建好之后，根据环境自行修改两份配置文件： [ [producer-properties](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-feign/producer/src/main/resources/application.properties) ] 、[ [consumer-properties](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-feign/consumer/src/main/resources/application.properties) ]

配置文件里面配置的都是基础的配置信息，具体的配置项还需详见官网。

### 代码说明

- producer：
```shell script
.
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── mengli
    │   │           └── producer
    │   │               ├── ProducerApplication.java : 启动类，添加配置注释：@EnableEurekaClien 开启EurekaClient；
    │   │               └── controller
    │   │                   └── HomeController.java : 测试Controller层代码；
    │   └── resources
    └───────── application.properties  : 配置文件
```
- consumer：
```shell script
.
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── mengli
    │   │           └── consumer
    │   │               ├── ConsumerApplication.java : 启动类，需要添加两个配置注释：@EnableEurekaClient @EnableFeignClients 分别是开启EurekaClient和FeignClient
    │   │               ├── controller
    │   │               │   └── HomeController.java : 测试Controller层代码；
    │   │               ├── hystrix
    │   │               │   └── HomeRemoterHystrix.java : 降级熔断实现，需要在配置文件声明开启 Hystrix。
    │   │               └── remote
    │   │                   └── HomeRemoter.java  ： 远程连接producer的连接器，详解见代码。
    │   └── resources
    └───────── application.properties  : 配置文件
```