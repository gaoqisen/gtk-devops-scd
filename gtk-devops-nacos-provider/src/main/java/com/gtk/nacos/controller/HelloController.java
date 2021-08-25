package com.gtk.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nacos")
public class HelloController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("hi/{message}")
    public String hi(@PathVariable String message){
        return "nacos: " + port + ", message: " + message;
    }

}
