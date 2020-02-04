package com.gtk.nacos.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gtk.dubbo.provider.service.ProviderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {


    @Reference
    private ProviderService providerService;

    @RequestMapping("hi")
    public String hi() {
        return providerService.hi();
    }

}
