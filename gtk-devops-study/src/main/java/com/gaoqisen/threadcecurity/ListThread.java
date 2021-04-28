package com.gaoqisen.threadcecurity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

// 线程安全
public class ListThread {

    static final int count = 1000;

    public static void main(String[] args) throws InterruptedException {

        // 线程不安全
        for (int i = 0; i < 10; i++) {
            threadHandleArrayList();
        }

        // 线程安全
        for (int i = 0; i < 10; i++) {
            threadHandleVector();
        }

    }
    static void threadHandleVector() throws InterruptedException {
        List<Integer> list = new Vector();
        // 用来让主线程等待threadCount个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread("线程"+ (i+1)){
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        list.add(1);
                    }
                }
            }.start();
        }
        Thread.sleep(100);
        System.out.println(list.size());
    }

    static void threadHandleArrayList() throws InterruptedException {
        List<Integer> list = new ArrayList();
        // 用来让主线程等待threadCount个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread("线程"+ (i+1)){
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        list.add(1);
                    }
                }
            }.start();
        }
        Thread.sleep(100);
        System.out.println(list.size());
    }

}
