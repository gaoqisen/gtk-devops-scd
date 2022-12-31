package com.gaoqisen.io.net.rpcdemo.service.impl;

import com.gaoqisen.io.net.rpcdemo.service.Car;
import com.gaoqisen.io.net.rpcdemo.service.Persion;

public class MyCar implements Car {

    @Override
    public String open(String name) {
        System.out.println("实现类收到");
        return "server res > " + name;
    }

    @Override
    public Persion get(String age, String name) {
        Persion persion = new Persion();
        persion.setAge(age);
        persion.setName(name);
        return persion;
    }
}