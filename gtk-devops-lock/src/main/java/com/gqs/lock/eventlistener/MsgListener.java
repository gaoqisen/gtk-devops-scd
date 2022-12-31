package com.gqs.lock.eventlistener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MsgListener implements ApplicationListener<MsgEvent> {

    @Override
    public void onApplicationEvent(MsgEvent msgEvent) {
        System.out.println("实现监听器方式" + msgEvent.getMsg() + msgEvent.getName());
    }
}
