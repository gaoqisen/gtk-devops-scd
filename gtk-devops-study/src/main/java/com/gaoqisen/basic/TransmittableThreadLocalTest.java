package com.gaoqisen.basic;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransmittableThreadLocalTest {

    // 线程池创建两个线程
    private static ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));

    public static void main(String[] args) throws InterruptedException {
        InheritableValue.set("123");

        new Thread( () -> {
            for (int i = 0; i < 10; i++) {
                executorService.execute(TransmittableThreadLocalTest::sleepPrint);
            }
        }).start();

        sleepPrint();
        InheritableValue.set("456");
        new Thread( () -> {
            for (int i = 0; i < 10; i++) {
                executorService.execute(TransmittableThreadLocalTest::sleepPrint);
            }
        }).start();

        // 使用完成之后一定要remove否则会内存泄漏。线程池需要注意remove的执行时间
        Thread.sleep(11000L);
        InheritableValue.clear();
    }

    public static void sleepPrint() {
        try {
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread().getName() + ", 线程执行完毕获取" + InheritableValue.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
// 支持线程池的ThreadLocal,同时支持非线程池
class InheritableValue {

    private static ThreadLocal<Hello> TTL = new TransmittableThreadLocal<>();

    public static void set(String val) {
        Hello hello = new Hello();
        hello.setName(val);
        TTL.set(hello);
    }

    public static Hello getHello() {
        return TTL.get();
    }

    public static String get(){
        return TTL.get().getName();
    }

    public static void clear() {
        System.out.println("执行结束！");
        TTL.remove();
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
