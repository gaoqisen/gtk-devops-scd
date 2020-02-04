package com.gtk.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * fallback 指定熔断类
 */
@FeignClient(value = "nacos-provide", fallback = FeignServiceFallback.class)
public interface FeignService {

    /**
     * nacos 自动实现负载均衡
     * @param message
     * @return
     */
    @RequestMapping("/nacos/hi/{message}")
    String hi(@PathVariable String message);


}
