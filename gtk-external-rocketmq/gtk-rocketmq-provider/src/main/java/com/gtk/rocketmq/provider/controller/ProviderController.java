package com.gtk.rocketmq.provider.controller;

import com.gtk.rocketmq.provider.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    /**
     * 报错：close the connection to remote address[] result: true
     * 解决方案是在 broker.conf 配置文件中增加 brokerIP1=宿主机IP（ifconfig，本地内网IP） 即可
     * @param msg
     * @return
     */
    @GetMapping("hi")
    public String hi(String msg) {
        providerService.send(msg);
        return "ok";
    }

}
