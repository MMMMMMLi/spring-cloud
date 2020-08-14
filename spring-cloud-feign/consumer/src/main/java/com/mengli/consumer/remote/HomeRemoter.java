package com.mengli.consumer.remote;

import com.mengli.consumer.config.MineFeignConfig;
import com.mengli.consumer.hystrix.HomeRemoterHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer", fallback = HomeRemoterHystrix.class, configuration = {MineFeignConfig.class})
//@RequestMapping("/producer")
public interface HomeRemoter {

    @RequestMapping("/producer/hello")
    public String hello(@RequestParam(value = "name") String name);

    @RequestMapping("/producer/bye")
    public String bye();
}
