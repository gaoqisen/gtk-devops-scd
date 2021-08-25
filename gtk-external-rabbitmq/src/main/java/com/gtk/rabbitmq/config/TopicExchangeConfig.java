package com.gtk.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// topic 是RabbitMQ中最灵活的一种方式，可以根据routing_key自由的绑定不同的队列
public class TopicExchangeConfig {

    final static String message = "topic.message";
    final static String messages = "topic.messages";


    //创建两个 Queue
    @Bean
    public Queue queueMessage(){
        return new Queue(TopicExchangeConfig.message);
    }

    @Bean
    public Queue queueMessages(){
        return new Queue(TopicExchangeConfig.messages);
    }

    //配置 TopicExchange,指定名称为 topicExchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("topicExchange");
    }

    //给队列绑定 exchange 和 routing_key

    @Bean
    public Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    public Binding bingingExchangeMessages(Queue queueMessages,TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

}
