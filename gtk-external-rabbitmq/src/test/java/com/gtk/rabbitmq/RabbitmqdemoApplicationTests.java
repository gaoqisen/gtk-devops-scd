package com.gtk.rabbitmq;

import com.gtk.rabbitmq.message.MessageSender;
import com.gtk.rabbitmq.message.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqdemoApplicationTests {

    @Autowired
    MessageSender messageSender;

    @Autowired
    TopicSender topicSender;

    @Test
    public void message() {
        messageSender.send();

    }

    @Test
    public void topic() {
        topicSender.send1();
        topicSender.send2();
    }

}
