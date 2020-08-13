## 说明
Spring Cloud Eureka 是 Spring Cloud Netflix 组件的一部分。

它基于Netflix Eureka做了二次封装，来实现微服务架构中的服务治理功能，即服务注册和发现等功能。

Eureka Server 作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，作为 Eureka 的客户端连接到 Eureka Server，并维持心跳连接。

这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。Spring Cloud 的一些其他模块（比如Zuul）就可以通过 Eureka Server 来发现系统中的其他微服务，并执行相关的逻辑。

Eureka由两个组件组成：Eureka服务器和Eureka客户端。

Eureka服务器也叫作服务注册中心。

Eureka客户端是一个java客户端，用来简化与服务器的交互、作为轮询负载均衡器，并提供服务的故障切换支持。

Netflix在其生产环境中使用的是另外的客户端，它提供基于流量、资源利用率以及出错状态的加权负载均衡。