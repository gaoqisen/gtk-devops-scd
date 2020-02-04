package com.gtk.consumer.service;

import org.springframework.stereotype.Component;

@Component
public class FeignServiceFallback implements FeignService{
    @Override
    public String hi(String message) {
        return "熔断器回调";
    }
}
