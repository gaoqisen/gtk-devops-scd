package com.gaoqisen.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

public class PhantomsReference {

    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    private static final List<Object> list = new LinkedList<>();

    public static void main(String[] args) {
        // 用来管理直接内存（在对象被垃圾回收之后会执行一些操作）
        // 看m对象有没有指向堆外内存(不归gc管理的)，如果后指向，则清理堆外内存。
        PhantomReference<M> m = new PhantomReference<>(new M(), QUEUE);
        System.out.println(m.get());

        new Thread( () -> {
            while(true) {
                list.add(new byte[1024*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(m.get());
            }
        }).start();

        // 垃圾回收线程
        new Thread(()->{
            while (true) {
                Reference<? extends M> poll = QUEUE.poll();
                if(poll != null) {
                    System.out.println("虚引用被jvm回收" + poll);
                }
            }
        }).start();

    }
}
