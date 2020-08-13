package com.mengli.mineactuator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String Home() {
        return "Hello Actuator With Security";
    }

    @RequestMapping("/test")
    public String HomeTest() {
        return "Hello Actuator With Securit Test";
    }

}
