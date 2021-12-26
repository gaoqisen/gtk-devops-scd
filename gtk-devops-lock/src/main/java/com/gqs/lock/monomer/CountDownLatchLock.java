package com.gqs.lock.monomer;

import java.util.concurrent.CountDownLatch;

/**
 * 门栓
 */
public class CountDownLatchLock {

    public static void main(String[] args) throws InterruptedException {


        CountDownLatch count = new CountDownLatch(1);
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("线程处理: " + i);
                if(i == 70) {
                    try {
                        count.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        Thread.sleep(1000);
        System.out.println("主线程释放");
        count.countDown();

    }

}
