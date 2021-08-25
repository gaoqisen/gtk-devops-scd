package com.gaoqisen.basic;

public class ThreadLocalTest {

    // 定义ThreadLocal
    private static ThreadLocal<String> TL = new ThreadLocal<>();

    public static void main(String[] args) {
        TL.set("123");
        new Thread( () -> {
            // 无法获取TL里面的值
            System.out.println("子线程" + Thread.currentThread().getName() + "： " + TL.get());
        }).start();
        fun();

        // 使用完成之后一定要remove否则会内存泄漏
        TL.remove();
    }

    // 可以获取TL里面的值
    public static void fun() {
        System.out.println("父线程："+ Thread.currentThread().getName() + "： " + TL.get());
    }

}
