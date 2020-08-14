package com.mengli.producer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class HomController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name") String name) {
        return "Hello " + name + " , I'm Hello Method !";
    }

    @RequestMapping("/bye")
    public String bye() {
        return "Bye , Nice to meet you ~ ";
    }

}
