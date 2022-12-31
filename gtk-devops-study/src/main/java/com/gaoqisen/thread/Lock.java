package com.gaoqisen.thread;

public class Lock {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread("线程1"){
            @Override
            public void run() {
                synchronized (a) {
                    try {
                        System.out.println("给对象a加锁并访问对象b");
                        Thread.sleep(500);
                        synchronized (b) {
                            System.out.println("获取对象b");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread("线程2"){
            @Override
            public void run() {
                synchronized (b) {
                    try {
                        System.out.println("给对象b加锁并访问对象a");
                        Thread.sleep(500);
                        synchronized (a) {
                            System.out.println("获取对象a");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
