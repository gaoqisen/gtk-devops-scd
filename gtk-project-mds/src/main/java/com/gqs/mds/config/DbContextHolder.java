package com.gqs.mds.config;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * TransmittableThreadLocal的底层是ThreadLocal(TL),是线程私有的。
 * 在接口数据请求的时候是一个独立的线程，在线程里面用TL切换数据源是线程安全的
 */
public class DbContextHolder {

    private static final TransmittableThreadLocal<String> TTL = new TransmittableThreadLocal<>();

    public static void setVal(String val) {
        TTL.set(val);
    }

    public static String getVal() {
        return TTL.get();
    }

    public static void clear() {
        TTL.remove();
    }

}
