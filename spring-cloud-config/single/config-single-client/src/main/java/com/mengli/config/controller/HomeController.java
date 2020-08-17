package com.mengli.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${project.name}")
    private String name;

    @Value("${project.version}")
    private String version;

    @RequestMapping("/hello")
    public String Hello() {
        return "Hello, " + name + "I'm " + version;
    }
}
