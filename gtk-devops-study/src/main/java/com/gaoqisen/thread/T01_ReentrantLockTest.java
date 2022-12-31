package com.gaoqisen.thread;

import java.util.concurrent.locks.ReentrantLock;

public class T01_ReentrantLockTest {

    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        T01_ReentrantLockTest reentrantLockTest = new T01_ReentrantLockTest();
        reentrantLockTest.m1();
    }

    public void m1() {
     //   new Thread(() -> {
            try{
                lock.lock();
                System.out.println("m1...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                m2();
            }finally {
                lock.unlock();
            }
    //    }).start();
    }

    public void m2() {
     //   new Thread(() -> {
            try{
                lock.lock();
                System.out.println("m2...");
            } finally {
                lock.unlock();
            }
   //     }).start();
    }

}
