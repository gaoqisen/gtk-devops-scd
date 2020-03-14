package com.gtk.rabbitmq.message;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReceiver3 {


    @RabbitHandler
    public void process(String message){

        System.out.println("C: "+message);

    }

}
