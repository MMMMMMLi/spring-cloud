package com.mengli.haconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
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
