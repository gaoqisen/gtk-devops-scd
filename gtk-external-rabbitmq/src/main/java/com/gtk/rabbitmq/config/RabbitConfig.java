package com.gtk.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        return new Queue("rabbitmq_message");
    }
    // 可配置多个队列，实现一对多，多对一
    @Bean
    public Queue queue2(){
        return new Queue("rabbitmq_message2");
    }
}
