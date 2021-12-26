package com.gqs.lock.monomer;

public class SynchronizedLock {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 需要和synchronized一起使用， 可以实现在一个线程中登陆另一个线程去唤醒
         */
        waitAndNotify();

        /**
         * 睡眠指定时间后执行
         */
        Thread.sleep(2000);
        System.out.println("yield----------------");

        /**
         * 让步：
         *
         * 让当前处于运行状态下的线程转入就绪状态
         * 实际中无法保证yield()达到让步的目的，因为，让步的线程可能被线程调度程序再次选中
         */
        yield();

        Thread.sleep(2000);
        System.out.println("join----------------");

        /**
         * 等待join的线程执行完成后在执行
         */
        join();


    }

    private static void join() throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("join线程1: " + i);
            }
        });
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("join线程2: " + i);
            }
        }).start();
        t.start();
        Thread.sleep(2000);
    }


    private static void yield() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("线程1: " + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                 System.out.println("线程2: " + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();
    }

    private static void waitAndNotify() {
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                try {
                    System.out.println("线程1唤醒等待资源的线程");
                    o.notify();
                    System.out.println("线程1让出时间片并释放锁， 等待notify(),notifyAll()释放锁");
                    o.wait();
                    System.out.println("线程1结束");
                    o.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (o) {
                try {
                    System.out.println("线程2唤醒等待资源的线程");
                    o.notifyAll();
                    System.out.println("线程2让出时间片并释放锁， 等待notify(),notifyAll()释放锁");
                    o.wait();
                    System.out.println("线程2结束");
                    o.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("主线程结束");
    }

}
