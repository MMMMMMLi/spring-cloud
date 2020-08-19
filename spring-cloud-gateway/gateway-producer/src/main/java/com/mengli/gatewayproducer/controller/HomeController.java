package com.mengli.gatewayproducer.controller;

import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/producer")
public class HomeController {

    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        System.out.println("Name is :" + name);
        if (name != "" || !name.isEmpty()) {
            throw new RuntimeException("error");
        }
        return "Hello " + name + " This is Producer Service ~";
    }

    @RequestMapping("/token")
    public String hello(@RequestParam String name, @RequestParam String token) {
        return "Hello " + name + ", You token is : " + token;
    }
}
