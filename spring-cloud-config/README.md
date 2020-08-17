## 说明

`学习的时候，对这个config组件存在疑惑，到底有什么作用，为什么要用这个东西，线上实际环境到底会不会用到？应用场景是怎么样的？ By: 2020年08月14日 17:02:32`

---
当前项目内主要有两个版本：
```shell script
ha：整合了Eureka搭建的高可用版本；
single：单机测试版本。

一般项目使用的话就是使用 HA 版。
```
---

Spring Cloud Config是用来为分布式系统中的基础设施和微服务应用提供集中化的外部配置支持，它分为服务端与客户端两个部分。

服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置仓库并为客户端提供获取配置信息、加密/解密信息等访问接口；

客户端则是微服务架构中的各个微服务应用或基础设施，它们通过指定的配置中心来管理应用资源与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息。

Spring Cloud Config也提供本地存储配置的方式。

只需要在配置文件中设置属性：`spring.profiles.active=native`，Config Server会默认从应用的`src/main/resource` 目录下检索配置文件。

也可以通过`spring.cloud.config.server.native.searchLocations=file:E:/properties/` 属性来指定配置文件的位置。

虽然Spring Cloud Config提供了这样的功能，但是为了支持更好的管理内容和版本控制的功能，还是推荐使用git的方式。

```shell script
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

```shell script
# --------------
# 此处动态获取配置文件的几个必须点是:
# 1. 在client端编写配置文件{bootstrap.properties}的时候,里面几个配置项的属性.
#       spring.cloud.config.name,这个的属性,就对应代码仓库里面的具体的某个url,即下面{application}对应的;
# 2. 所有的配置文件命名必须以:application-{profile}.properties命名,这样在查找配置文件的时候就会根据{profile}找到.
spring.cloud.config.server.git.uri=https://github.com/MMMMMMLi/{application}
# 这个配置项的使用场景：一套{spring-cloud-config}的服务，可以适配所有的微服务项目。
#
# 使用这个配置项，在项目启动的时候，会抛出错误信息，因为它在启动的时候，需要检测连接状态，并且默认的{application}为 app
# 解决方案：
# 1. 在代码仓库下命名一个 app 的仓库目录；
# 2. 配置一个已存在的仓库来进行连通检测，如下所示，它实现了通过连接check-repo-config仓库进行健康检测
# spring.cloud.config.server.health.repositories.check.name=check-repo
# spring.cloud.config.server.health.repositories.check.label=master
# spring.cloud.config.server.health.repositories.check.profiles=default
# 3. 关闭健康检查
spring.cloud.config.server.health.enabled=false
# --------------
```