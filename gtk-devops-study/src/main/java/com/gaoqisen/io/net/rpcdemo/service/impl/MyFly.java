package com.gaoqisen.io.net.rpcdemo.service.impl;

import com.gaoqisen.io.net.rpcdemo.service.Fly;

public class MyFly implements Fly {

    @Override
    public void open(String name) {
        System.out.println("实现类收到");
    }
}
