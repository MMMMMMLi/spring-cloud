package com.mengli.consumer.hystrix;

import com.mengli.consumer.remote.HomeRemoter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HomeRemoterHystrix implements HomeRemoter {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return name + " Sorry, Producer is over .";
    }

    @Override
    public String bye() {
        return "bbbbbbbbbbbbbbbbbbbbbbbbbbbye ";
    }
}
