package com.gaoqisen.io.net.rpcdemo.rpc;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 调度员
 *
 * provider存放所有的被代理类
 *  1. provider可以获取到
 *  2. customer获取不到
 *
 */
public class Dispatcher{

    private static Dispatcher dis = null;

    static  {
        dis = new Dispatcher();
    }

    public static Dispatcher getDis() {
        return dis;
    }

    public Dispatcher() {

    }

    public static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String k, Object obj) {
        invokeMap.put(k, obj);
    }

    public Object get(String k) {
        return invokeMap.get(k);
    }


}
