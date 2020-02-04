package com.gtk.consumer.controller;

import com.gtk.consumer.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * feign 消费rest接口
 */
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    @GetMapping("feign/{message}")
    public String hi(@PathVariable String message) {
        return feignService.hi(message);
    }

}
