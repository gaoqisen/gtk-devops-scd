package com.gtk.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gtk.dubbo.provider.service.ProviderService;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ProviderServiceImpl implements ProviderService
{
    @Value("${server.port}")
    private String port;

    @Override
    public String hi() {
        return "服务提供者提供" + port;
    }
}
