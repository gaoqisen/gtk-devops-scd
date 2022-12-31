package com.gqs.lock.eventlistener;

import org.springframework.context.ApplicationEvent;


public class MsgEvent extends ApplicationEvent {

    private String name;

    private String msg;

    public MsgEvent(Object source) {
        super(source);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
