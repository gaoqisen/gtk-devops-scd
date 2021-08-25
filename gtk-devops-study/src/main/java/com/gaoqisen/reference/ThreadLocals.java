package com.gaoqisen.reference;

import java.util.concurrent.TimeUnit;

public class ThreadLocals {

    // 线程私有的， 只有在同一个线程里面才可以拿到值
    private static ThreadLocal<People> th = new ThreadLocal<>();

    public static void main(String[] args){
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("th: " + th.get());
        }).start();

        new Thread(() ->{
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 在当前线程的map对象里面放 map.set(this, value);
            // set的时候Entry继承了WeakReference，防止内存泄漏。弱引用，只要没有对象指向
            th.set(new People());
            // 不用了一定要remove，不然也会内存泄漏
            th.remove();
        }).start();
    }

}

class People {
    private String name = "t";

}