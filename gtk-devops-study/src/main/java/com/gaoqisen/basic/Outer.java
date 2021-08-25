package com.gaoqisen.basic;

public class Outer {

    private String name = "tom";

    private static String age = "15";

    // 静态内部类(不能访问非静态成员变量)
    public static class StaticInnerClass{
        public String getAge() {
            return age;
        }
    }

    // 内部类
    public class InnerClass{
        public String getInfo() {
            return name + age;
        }
    }

    public static void main(String[] args) {
        Outer.age = "18";
        // 实例化静态内部类
        Outer.StaticInnerClass staticInnerClass = new Outer.StaticInnerClass();
        System.out.println(staticInnerClass.getAge());

        // 实例化非静态内部类
        Outer.InnerClass innerClass = new Outer().new InnerClass();
        System.out.println(innerClass.getInfo());

        float a = 9.0f;
        int b = 5;
        b++;
        System.out.println(b++);
        System.out.println(b);

    }

}
