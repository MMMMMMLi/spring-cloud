## 说明
Spring Cloud Eureka 是 Spring Cloud Netflix 组件的一部分。

它基于Netflix Eureka做了二次封装，来实现微服务架构中的服务治理功能，即服务注册和发现等功能。

Eureka Server 作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，作为 Eureka 的客户端连接到 Eureka Server，并维持心跳连接。

这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。Spring Cloud 的一些其他模块（比如Zuul）就可以通过 Eureka Server 来发现系统中的其他微服务，并执行相关的逻辑。

Eureka由两个组件组成：Eureka服务器和Eureka客户端。

Eureka服务器也叫作服务注册中心。

Eureka客户端是一个java客户端，用来简化与服务器的交互、作为轮询负载均衡器，并提供服务的故障切换支持。

Netflix在其生产环境中使用的是另外的客户端，它提供基于流量、资源利用率以及出错状态的加权负载均衡。

## 配置文件参数

Eureka的配置参数主要分了三大部分：`server`，`client`，`instance` 。

下面列的参数是一些常用的，全部的配置参数详见官网吧。

也可以查看自动配置类中的属性参数
```shell script
server: 
  org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean
client: 
  org.springframework.cloud.netflix.eureka.EurekaClientConfigBean
instance: 
  org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean
```


- `server`

| 参数                                        | 描述                         | 备注       |
| ------------------------------------------- | ---------------------------- | ---------- |
| eureka.server.eviction-interval-timer-in-ms | server清理无效节点的时间间隔 | 默认60秒   |
| eureka.server.enable-self-preservation      | 是否开启自我保护，默认true   | true false |
| eureka.server.renewal-percent-threshold     | 开启自我保护的系数           | 默认：0.85 |

- `instance`

| 参数                                                 | 描述                                 | 备注                                                         |
| ---------------------------------------------------- | ------------------------------------ | ------------------------------------------------------------ |
| eureka.instance.lease-renewal-interval-in-seconds    | 服务续约任务调用间隔时间，默认30秒   | client每隔30秒向server上报自己状态，避免被server剔除         |
| eureka.instance.lease-expiration-duration-in-seconds | 服务时效时间，默认90秒               | 当server 90秒内没有收到client的注册信息时，会将该节点剔除    |
| eureka.client.registry-fetch-interval-seconds        | client本地缓存清单更新间隔，默认30秒 | client每隔30秒，向server请求可用服务清单。对于API网关类应用，可以适当降低时间间隔 |
| eureka.instance.prefer-ip-address                    | 注册服务时是否使用IP注册，默认false  | true false                                                   |
| eureka.instance.ip-address                           | server端的ip地址                     |                                                              |
| eureka.instance.hostname                             | server端的hostname                   | 默认localhost                                                |
| eureka.instance.instance-id                          | 注册到server的实例                   |                                                              |

- `client`

| 参数                                                   | 描述                           | 备注          |
| ------------------------------------------------------ | ------------------------------ | ------------- |
| eureka.client.enabled                                  | 是否开启client，默认true       | true false    |
| eureka.client.register-with-eureka                     | 是否注册                       | 默认true      |
| eureka.client.fetch-registry                           | 是否检索服务                   | true false    |
| eureka.client.serviceUrl.defaultZone                   | 默认服务注册中心地址           | 多个用","隔开 |
| eureka.client.eureka-server-connect-timeout-seconds    | 连接server服务器超时时间       | 默认5秒       |
| eureka.client.eureka-connection-idle-timeout-seconds   | 连接server的连接空闲时长       | 默认30秒      |
| eureka.client.eureka-server-read-timeout-seconds       | 连接server读取数据超时时间     | 默认8秒       |
| eureka.client.eureka-server-total-connections          | 连接server的最大连接数         | 默认200       |
| eureka.client.eureka-server-total-connections-per-host | 对单个server的最大连接数       | 默认50        |
| eureka.client.eureka-service-url-poll-interval-seconds | 获取集群中最新的server节点数据 | 默认0         |
| eureka.client.heartbeat-executor-thread-pool-size      | client维持与server的心跳线程数 | 默认2         |
| eureka.client.service-url                              | 列出所有可用注册中心的地址     |               |