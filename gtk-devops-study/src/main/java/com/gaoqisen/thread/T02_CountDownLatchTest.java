package com.gaoqisen.thread;

import java.util.concurrent.CountDownLatch;

public class T02_CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                System.out.println("处理数据:" + i);
                countDownLatch.countDown();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("门拴打开了");
    }

}
