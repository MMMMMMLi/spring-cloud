## 说明

适用`Spring-Cloud`的网关服务主要有两种，`zuul && gateway`，由于学习的Cloud版本为 `Hoxton.SR7`，所以就不具体的记录和学习 `zuul` 的相关知识了，单纯的扫下盲即可。

> 此项目依赖服务

[spring-cloud-eureka](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-eureka)

---

### API网关

微服务架下，服务之间容易形成网状的调用关系，这种网状的调用关系不便管理和维护，这种场景下API网关应运而生。作为后端服务的入口，API网关在微服务架构中尤其重要，在对外部系统提供API入口的要求下，API网关应具备路由转发、负载均衡、限流熔断、权限控制、轨迹追踪和实时监控等功能。

目前，很多微服务都基于的Spring Cloud生态构建。Spring Cloud生态为我们提供了两种API网关产品，分别是Netflix开源的Zuul1和Spring自己开发的Spring Cloud Gateway 。

Spring Cloud以Finchley版本为分界线，Finchley版本发布之前使用Zuul1作为API网关，之后更推荐使用Gateway。

#### Zuul

Zuul1是Netflix在2013年开源的网关组件，大规模的应用在Netflix的生产环境中，经受了实践考验。它可以与Eureka、Ribbon、Hystrix等组件配合使用，实现路由转发、负载均衡、熔断等功能。

Zuul1的核心是一系列过滤器，过滤器简单易于扩展，已经有一些三方库如`spring-cloud-zuul-ratelimit` 等提供了过滤器支持。

Zuul1基于Servlet构建，使用的是阻塞的IO，引入了线程池来处理请求。每个请求都需要独立的线程来处理，从线程池中取出一个工作线程执行，下游微服务返回响应之前这个工作线程一直是阻塞的。

#### Gateway

Spring Cloud Gateway 是Spring Cloud的一个全新的API网关项目，目的是为了替换掉Zuul1。

Gateway可以与Spring Cloud Discovery Client（如Eureka）、Ribbon、Hystrix等组件配合使用，实现路由转发、负载均衡、熔断等功能，并且Gateway还内置了限流过滤器，实现了限流的功能。

Gateway基于Spring 5、Spring boot 2和Reactor构建，使用Netty作为运行时环境，比较完美的支持异步非阻塞编程。Netty使用非阻塞的IO，线程处理模型建立在主从Reactors多线程模型上。

#### 对比

| 对比项           | Zuul1.x                                                      | Gateway                                                      |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 实现             | 基于Servlet2.x构建，使用阻塞的API。                          | 基于Spring 5、Project Reactor、Spring Boot 2，使用非阻塞式的API。 |
| 长连接           | 不支持                                                       | 支持                                                         |
| 不适用场景       | 后端服务响应慢或者高并发场景下，因为线程数量是固定（有限）的，线程容易被耗尽，导致新请求被拒绝。 | 中小流量的项目，使用Zuul1.x更合适。                          |
| 限流             | 无                                                           | 内置限流过滤器                                               |
| 上手难度         | 同步编程，上手简单                                           | 门槛较高，上手难度中等                                       |
| Spring Cloud集成 | 是                                                           | 是                                                           |
| Sentinel集成     | 是                                                           | 是                                                           |
| 技术栈沉淀       | Zuul1开源近七年，经受考验，稳定成熟。                        | 未见实际落地案例                                             |

上面的对比仅仅是一些简单的知识比对，网上有具体的性能对比文档，[具体参看相关博客](https://www.jianshu.com/p/3c40b603673f)。

## Spring Cloud Gateway

### 术语

- Route（路由）：这是网关的基本构建块。它由一个 ID，一个目标 URI，一组断言和一组过滤器定义。如果断言为真，则路由匹配。
- Predicate（断言）：这是一个 [Java 8 的 Predicate](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)。输入类型是一个 `ServerWebExchange` 。我们可以使用它来匹配来自 HTTP 请求的任何内容，例如 headers 或参数。
- Filter（过滤器）：这是`org.springframework.cloud.gateway.filter.GatewayFilter` 的实例，我们可以使用它修改请求和响应。

### Route

Gateway配置路由的方式很简单，就是在配置文件中，声明一下配置参数即可。

简单版如下：

```shell script
# 是否与服务注册于发现组件进行结合，通过 serviceId 转发到具体的服务实例
spring.cloud.gateway.discovery.locator.enabled=true
# 配置一下自定义路由规则
spring.cloud.gateway.routes[0].id=producerRoutes
# 格式为：lb://应用注册服务名
spring.cloud.gateway.routes[0].uri=lb://gateway-producer
# 级别越小约先执行
spring.cloud.gateway.routes[0].order=0
# 这个是配置路由规则的
spring.cloud.gateway.routes[0].predicates[0]=Path=/producer/**
```

也可以通过代码来配置，具体代码见启动类第一个注释`// Simple`

### Filter

当使用微服务构建整个 API 服务时，一般有许多不同的应用在运行，这些服务都需要对客户端的请求的进行验证。

一般有两种方法：
1. 为每个微服务应用都实现一套用于校验的过滤器或拦截器。
2. 通过前置的网关服务来完成这些非业务性质的校验。

一般使用第二种，即编写自定义的过滤器来实现，自定义过滤器需要实现`GatewayFilter`和`Ordered`，详见代码。

但是通过实现`GatewayFilter`实现的过滤器，仅仅是针对某一个路由来实现的，而不是全局的，如果要编写全局路由的话，需要实现`GlobalFilter`.

### 限流

限流这个东西，对于高并发下的场景很关键，但是目前没有实际接触的场景，后续补充吧。

一般开发高并发系统常见的限流有：限制总并发数（比如数据库连接池、线程池）、限制瞬时并发数（如 nginx 的 limit_conn 模块，用来限制瞬时并发连接数）、限制时间窗口内的平均速率（如 Guava 的 RateLimiter、nginx 的 limit_req 模块，限制每秒的平均速率）；其他还有如限制远程接口调用速率、限制 MQ 的消费速率。另外还可以根据网络连接数、网络流量、CPU 或内存负载等来限流。

### 降级

当下游服务出现异常宕机时，为了不影响整体系统的使用，Gateway结合Hystrix也可以实现降级熔断功能。

引用`Hystrix`的Maven坐标，配置文件中配置一下过滤器。
```shell script
spring.cloud.gateway.routes[0].filters[4].name=Hystrix
spring.cloud.gateway.routes[0].filters[4].args.name=fallbackcmd
# fallback 对应的 uri，这里的 uri 仅支持 forward: schemed 的
spring.cloud.gateway.routes[0].filters[4].args.fallbackUri=forward:/fallback
```
编写降级的端点`fallback`即可，详见代码。

这是一个简单的配置，如果需要更强大的功能，可以参考`HystrixGatewayFilterFactory`进行自定义。

### 重试

在一些特殊场景下，会存在失败重试的情景，所以这个配置也可参考。

```shell script
spring.cloud.gateway.routes[0].filters[5].name=Retry
# 重试次数，默认值是 3 次
spring.cloud.gateway.routes[0].filters[5].args.retries=1
# HTTP 的状态返回码，取值请参考：org.springframework.http.HttpStatus
spring.cloud.gateway.routes[0].filters[5].args.statuses=OK
# 指定哪些方法的请求需要进行重试逻辑，默认值是 GET 方法，取值参考：org.springframework.http.HttpMethod
spring.cloud.gateway.routes[0].filters[5].args.methods=GET
# 一些列的状态码配置，取值参考：org.springframework.http.HttpStatus.Series。符合的某段状态码才会进行重试逻辑，默认值是 SERVER_ERROR，值是 5，也就是 5XX(5 开头的状态码)，共有5 个值。
spring.cloud.gateway.routes[0].filters[5].args.series=SERVER_ERROR
# 指定哪些异常需要进行重试逻辑，默认值是java.io.IOException
spring.cloud.gateway.routes[0].filters[5].args.exceptions=java.io.IOException
```

但是在设置重试机制之前，需要把熔断降级的超市时间调大，因为其默认是1000毫秒即1秒，这样如果设置重试次数过多，会直接调用降级方法。
```shell script
# 这种是设置全局的
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=6000
# 这种是设置了针对某一种熔断器，
hystrix.command.fallbackcmd.execution.isolation.thread.timeoutInMilliseconds=8000
# 主要根据的是hystrix.command.{name}.execution.xxxx 配置的。
# name指向配置的Hystrix熔断器。
```

### 更多配置项

Gateway配置文件中配置的Filter主要是来自以下包：

`spring-cloud-gateway-core: \org\springframework\cloud\gateway\filter\factory\*`   

类名对应的是配置文件：`spring.cloud.gateway.routes[0].filters[*].name={className}`

          
## Spring Cloud Gateway Configuration 

这里将已经配置过的gateway的配置项做一个总结整理：

```properties
# 是否与服务注册于发现组件进行结合，通过 serviceId 转发到具体的服务实例
spring.cloud.gateway.discovery.locator.enabled=true
# 配置一下自定义路由规则
spring.cloud.gateway.routes[0].id=producerRoutes
# 格式为：lb://应用注册服务名
spring.cloud.gateway.routes[0].uri=lb://gateway-producer
# 级别越小约先执行
spring.cloud.gateway.routes[0].order=0
# 这个是配置路由规则的
spring.cloud.gateway.routes[0].predicates[0]=Path=/producer/**

# 简单版的路由跳转的话，就一直服用routes[*] 即可，不同的请求转发到不同服务当中即可
# 下面就是一些负责的配置，过滤器啊，熔断器啊，重试机制等等操作 

# 这是过滤器
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1
spring.cloud.gateway.routes[0].filters[1]=PrefixPath=/producer
spring.cloud.gateway.routes[0].filters[2]=AddResponseHeader=X-Response-Default-Foo, Default-Bar
spring.cloud.gateway.routes[0].filters[3]=AddRequestParameter=token, mengli
# 熔断降级
spring.cloud.gateway.routes[0].filters[4].name=Hystrix
spring.cloud.gateway.routes[0].filters[4].args.name=fallbackcmd
spring.cloud.gateway.routes[0].filters[4].args.fallbackUri=forward:/fallback
# 配置熔断超时时间
#hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=6000
hystrix.command.fallbackcmd.execution.isolation.thread.timeoutInMilliseconds=8000
# 重试
spring.cloud.gateway.routes[0].filters[5].name=Retry
# 重试次数，默认值是 3 次
spring.cloud.gateway.routes[0].filters[5].args.retries=1
# HTTP 的状态返回码，取值请参考：org.springframework.http.HttpStatus
spring.cloud.gateway.routes[0].filters[5].args.statuses=OK
# 指定哪些方法的请求需要进行重试逻辑，默认值是 GET 方法，取值参考：org.springframework.http.HttpMethod
spring.cloud.gateway.routes[0].filters[5].args.methods=GET
# 一些列的状态码配置，取值参考：org.springframework.http.HttpStatus.Series。符合的某段状态码才会进行重试逻辑，默认值是 SERVER_ERROR，值是 5，也就是 5XX(5 开头的状态码)，共有5 个值。
spring.cloud.gateway.routes[0].filters[5].args.series=SERVER_ERROR
# 指定哪些异常需要进行重试逻辑，默认值是java.io.IOException
spring.cloud.gateway.routes[0].filters[5].args.exceptions=java.io.IOException
```
