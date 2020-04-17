package com.gtk.common.thread;

import java.util.concurrent.*;

public class ExecutorServiceUtils {

    public static void main(String[] args) {
        execute();
    }

    // newSingleThreadExecutor
    private static void execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 1. 方法 execute(Runnable) 接收壹個 java.lang.Runnable 对象作为参数，并且以异步的方式执行它。
        executorService.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("异步执行1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        System.out.println("异步执行2");

        // 2. 方法 submit(Runnable) 同样接收壹個 Runnable 的实现作为参数，
        // 但是会返回壹個 Future 对象。这個 Future 对象可以用于判断 Runnable 是否结束执行
        Future future = executorService.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("异步执行3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //如果任务结束执行则返回 null
        try {
            System.out.println("异步执行4, future.get()=" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        // 3 方法 submit(Callable)  Callable 的 call() 方法可以返回壹個结果
        Future future1 = executorService.submit(new Callable(){
            @Override
            public Object call() throws Exception {
                System.out.println("异步执行5");
                return "success";
            }
        });

        try {
            System.out.println("异步执行6 future.get() = " + future1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();



    }

}
