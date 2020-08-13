## 说明

在微服务架构的分布式环境中，要考虑发生故障的情况。

如果只是一个单节点的注册中心，如果它发生故障宕机了，就会导致整个微服务系统挂掉，所以需要构建一个高可用的注册中心。

Eureka Server在设计上就考虑到了高可用问题，在Eureka的服务治理设计中，每一个服务提供者也是服务消费者，服务注册中心也不例外。

## 配置文件说明

- [[application-single.properties](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-eureka/eureka-server/src/main/resources/application-single.properties)] : 单节点配置的配置文件；
- [多节点版]：
    - [[application-cluster-first.properties](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-eureka/eureka-server/src/main/resources/application-cluster-first.properties)] : 集群版配置的配置文件；
    - [[application-cluster-second.properties](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-eureka/eureka-server/src/main/resources/application-cluster-second.properties)] : 集群版配置的配置文件；
    - [[application-cluster-three.properties](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-eureka/eureka-server/src/main/resources/application-cluster-three.properties)] : 集群版配置的配置文件；

## 启动

- 单节点启动：`java -jar xxx.jar --spring.profiles.active=single`

- 多节点启动：
```shell script
java -jar xxx.jar --spring.profiles.active=first
java -jar xxx.jar --spring.profiles.active=second
java -jar xxx.jar --spring.profiles.active=three
```