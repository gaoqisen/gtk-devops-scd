package com.gaoqisen.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * 交替输出数组
 */
public class Alternate {

    static Thread thread1 = null, thread2 = null;

    public static void main(String[] args) throws InterruptedException {
        String[] numbers = new String[]{"1", "2", "3", "4", "5"};

        String[] letters = new String[]{"A", "B", "C", "D", "E"};

        System.out.println("第一种方法: lockSupport start !!!");
        lockSupportTest(numbers, letters);

        Thread.sleep(2000);

        System.out.println("第二种方法: synchronized notify wait start !!!");
        synchronizedTest(numbers, letters);
    }

    private static void synchronizedTest(String[] numbers, String[] letters) {
        final Object obj = new Object();

        thread1 = new Thread(() -> {
           for(String str : numbers) {
               synchronized (obj) {
                   System.out.println(str);
                   obj.notify();
                   objWait(obj);
               }
           }
        }, "T1");

        thread2 = new Thread(() -> {
            for(String str : letters) {
                synchronized (obj) {
                    System.out.println(str);
                    obj.notify();
                    objWait(obj);
                }
            }
        }, "T2");

        thread1.start();
        thread2.start();

    }

    private static void objWait(Object obj) {
        try {
            obj.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void lockSupportTest(String[] numbers, String[] letters) {
        thread1 = new Thread(() -> {
            for (String number : numbers) {
                // 输出t1 解锁t2 锁定t1
                System.out.println(number);
                LockSupport.unpark(thread2);
                LockSupport.park(thread1);
            }
        }, "T1");

        thread2 = new Thread(() -> {
            for (String str : letters) {
                // 锁定t2， 解锁后输出，解锁t1
                LockSupport.park(thread2);
                System.out.println(str);
                LockSupport.unpark(thread1);
            }
        }, "T2");

        thread1.start();
        thread2.start();
    }

}
