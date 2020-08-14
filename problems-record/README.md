Problems
----
记录一下在学习Spring-Cloud过程中遇到的一些错误以及问题。

---
> 错误说明：在使用Feign Hystrix实现出现熔断降级操作的时候，项目一直启动失败。

- 涉及项目：

[ [spring-cloud-feign/consumer](https://github.com/MMMMMMLi/spring-cloud/tree/master/spring-cloud-feign/consumer) ]

- 涉及代码：
```java
package com.mengli.consumer.remote;

import com.mengli.consumer.hystrix.HomeRemoterHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer", fallback = HomeRemoterHystrix.class)
@RequestMapping("/producer")
public interface HomeRemoter {

    @RequestMapping("/producer/hello")
    public String hello(@RequestParam(value = "name") String name);

    @RequestMapping("/producer/bye")
    public String bye();
}

```

- 错误提示：
```shell script
Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'com.mengli.consumer.remote.HomeRemoter' method 
com.mengli.consumer.remote.HomeRemoter#bye()
to { /producer/producer/bye}: There is already 'homeRemoterHystrix' bean method
```

- 错误分析：

虽然没有搞明白是为什么出现这种情况，但是看错误日志是因为在构建mapping的时候，重复扫描导致的。

- 错误解决：

将在接口`HomeRemoter`上类级别声明的`@RequestMapping`注释掉就可以。 

---