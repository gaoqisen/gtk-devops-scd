package com.gqs.lock.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0",interfaceClass = TestService.class)
public class TestServiceImpl implements TestService{
    @Override
    public String hello(String name) {
        return name + "你好";
    }
}
