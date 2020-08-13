## 说明

Actuator是从spring-boot中带的一套用来进行系统健康检查的一个模块，具有即插即用的能力。

它提供了一套基于restful的api接口，可以帮助我们方便的通过相应的接口了解到系统资源占用、beans、dump、mappings等相关的信息。

同时其还具有能力，可以基于HealthIndicators实现自定义的安全与健康检查。

## 默认端点服务

Spring Boot 2.0的端点基础路径由“/”调整到”/actuator”下,如：`/info` 调整为 `/actuator/info`

| ID             | 描述                                                         | 默认启用 |
| :--------------: | ------------------------------------------------------------ | -------- |
| auditevents    | 显示当前应用程序的审计事件信息                               | Yes      |
| beans          | 显示一个应用中所有Spring Beans的完整列表                     | Yes      |
| conditions     | 显示配置类和自动配置类(configuration and auto-configurationclasses)的状态及它们被应用或未被应用的原因 | YES      |
| configprops    | 显示一个所有@ConfigurationProperties的集合列表               | Yes      |
| env            | 显示来自Spring的 ConfigurableEnvironment的属性               | Yes      |
| flyway         | 显示数据库迁移路径，如果有的话                               | Yes      |
| health         | 显示应用的健康信息（当使用一个未认证连接访问时显示一个简单的’status’，使用认证连接访问则显示全部信息详情） | YES      |
| info           | 显示任意的应用信息                                           | Yes      |
| liquibase      | 展示任何Liquibase数据库迁移路径，如果有的话                  | Yes      |
| metrics        | 展示当前应用的metrics信息                                    | Yes      |
| mappings       | 显示一个所有@RequestMapping路径的集合列表                    | Yes      |
| scheduledtasks | 显示应用程序中的计划任务                                     | Yes      |
| sessions       | 允许从Spring会话支持的会话存储中检索和删除(retrieval and deletion)用户会话。使用Spring Session对反应性Web应用程序的支持时不可用。 | YES      |
| shutdown       | 允许应用以优雅的方式关闭（默认情况下不启用）                 | No       |
| threaddump     | 执行一个线程dump                                             | Yes      |

## 端点暴露

JMX：Java Management Extensions

| ID             | JMX  | Web  |
| :--------------: | :----: | :----: |
| auditevents    | Yes  | No   |
| beans          | Yes  | No   |
| conditions     | Yes  | No   |
| configprops    | Yes  | No   |
| env            | Yes  | No   |
| flyway         | Yes  | No   |
| health         | Yes  | Yes  |
| heapdump       | N/A  | No   |
| httptrace      | Yes  | No   |
| info           | Yes  | Yes  |
| jolokia        | Yes  | No   |
| logfile        | Yes  | No   |
| loggers        | Yes  | No   |
| liquibase      | Yes  | No   |
| metrics        | Yes  | No   |
| mappings       | Yes  | No   |
| prometheus     | N/A  | No   |
| scheduledtasks | Yes  | No   |
| sessions       | Yes  | No   |
| shutdown       | Yes  | No   |
| threaddump     | Yes  | No   |