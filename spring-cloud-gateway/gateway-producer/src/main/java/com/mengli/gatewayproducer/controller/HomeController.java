package com.mengli.gatewayproducer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/producer")
public class HomeController {

    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        return "Hello " + name + " This is Producer Service ~";
    }

    @RequestMapping("/token")
    public String hello(@RequestParam String name,@RequestParam String token) {
        return "Hello " + name + ", You token is : " +token;
    }
}
