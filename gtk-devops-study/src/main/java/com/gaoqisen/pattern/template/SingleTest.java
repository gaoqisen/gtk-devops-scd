package com.gaoqisen.pattern.template;

import java.util.concurrent.atomic.AtomicInteger;

public class SingleTest {

    private static final AtomicInteger THREAD = new AtomicInteger(0);

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(() -> {
                if(THREAD.get() != 0) {
                    System.out.println("被使用");
                    return;
                }
                THREAD.getAndIncrement();
                try {
                    System.out.println("使用中" + Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                THREAD.set(0);
            });
            thread.setName("当前线程".concat(i + ""));
            thread.start();
        }

    }

}
