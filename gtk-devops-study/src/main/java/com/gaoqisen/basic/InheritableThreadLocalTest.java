package com.gaoqisen.basic;

public class InheritableThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        InheritableVal.set("123");

        new Thread( () -> {
            // 修改对象引用里面的值即可共享父子线程的值，如果是基本类型则无法共享
            InheritableVal.Hello hello = InheritableVal.getHello();
            hello.setName("456465");
            // 可以获取TL里面的值
            System.out.println("线程执行完毕获取" + InheritableVal.get());
        }).start();

        System.out.println("睡眠前再次获取" + InheritableVal.get());
        Thread.sleep(1000L);
        System.out.println("睡眠后再次获取" + InheritableVal.get());

        // 使用完成之后一定要remove否则会内存泄漏
        InheritableVal.clear();
    }

}

class InheritableVal {

    private static ThreadLocal<Hello> ITL = new InheritableThreadLocal<>();

    public static void set(String val) {
        Hello hello = new Hello();
        hello.setName(val);
        ITL.set(hello);
    }

    public static Hello getHello() {
        return ITL.get();
    }

    public static String get(){
        return ITL.get().getName();
    }

    public static void clear() {
        ITL.remove();
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

