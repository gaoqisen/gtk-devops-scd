package com.gtk.rabbitmq.message;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "rabbitmq_message")
public class MessageReceiver {

    @RabbitHandler
    public void process(String message){
        System.out.println("消费消息:"+message);
    }

}
