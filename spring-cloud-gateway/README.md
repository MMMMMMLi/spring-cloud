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
