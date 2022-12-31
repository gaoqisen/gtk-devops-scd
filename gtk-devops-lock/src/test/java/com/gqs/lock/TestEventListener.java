package com.gqs.lock;

import com.gqs.lock.eventlistener.ApplicationContextUtil;
import com.gqs.lock.eventlistener.MsgEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEventListener {

    @Autowired
    private ApplicationContextUtil applicationContextUtil;

    @Test
    public void start() {
        MsgEvent msgEvent = new MsgEvent("事件");
        msgEvent.setMsg("测试了");
        msgEvent.setName("123");
        applicationContextUtil.get().publishEvent(msgEvent);
    }

}
