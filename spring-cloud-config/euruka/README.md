## 说明

在`config-client`的配置文件`botstrap.properties/yml`配置文件中

一定要配置好关于`euruka`的相关信息，要不然会找不到相关配置信息。

### 动态刷新配置

1. 需要引入`spring-boot-starter-actuator` 依赖包；
2. `application`配置文件中，需要将`refresh`端点设置为开放；
3. 在对应的`Controller`中添加注释：`@RefreshScope`开启动态刷新。