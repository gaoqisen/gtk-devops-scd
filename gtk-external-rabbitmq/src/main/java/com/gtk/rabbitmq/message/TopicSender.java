package com.gtk.rabbitmq.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 都是用topicExchange,并且绑定到不同的 routing_key
@Component
public class TopicSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send1(){
        String context = "发送1";
        System.out.println("发送 : " + context);
        amqpTemplate.convertAndSend("topicExchange","topic.message",context);
    }

    public void send2() {
        String context = "发送2";
        System.out.println("发送 : " + context);
        amqpTemplate.convertAndSend("topicExchange", "topic.messages", context);
    }
}