package com.mengli.consumer.controller;

import com.mengli.consumer.remote.HomeRemoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class HomeController {

    @Autowired
    private HomeRemoter homeRemoter;

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name") String name) {
        return homeRemoter.hello(name);
    }

    @RequestMapping("/bye")
    public String bye() {
       return homeRemoter.bye();
    }
}
