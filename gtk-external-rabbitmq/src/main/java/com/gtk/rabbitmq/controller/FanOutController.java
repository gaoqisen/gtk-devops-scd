package com.gtk.rabbitmq.controller;

import com.gtk.rabbitmq.message.FanoutSender;
import com.gtk.rabbitmq.message.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FanOutController {

    @Autowired
    private FanoutSender fanoutSender;

    /**
     * 发送send1会匹配到topic.#和topic.message 两个Receiver都可以收到消息，
     * 发送send2只有topic.#可以匹配所有只有Receiver2监听到消息
     * @return
     */
    @RequestMapping("send1")
    public String send() {
        fanoutSender.send();
        return "success";
    }

}
