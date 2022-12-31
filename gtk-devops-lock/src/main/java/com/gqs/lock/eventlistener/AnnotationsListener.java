package com.gqs.lock.eventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnnotationsListener {

    @EventListener
    public void handler(MsgEvent msgEvent) {
        System.out.println("注解方式" + msgEvent.getMsg() + msgEvent.getName());
    }

}
