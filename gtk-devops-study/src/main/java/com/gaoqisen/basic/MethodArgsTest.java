package com.gaoqisen.basic;

public class MethodArgsTest {

    private String name;

    private String age;

    private static MethodArgsTest test;

    public MethodArgsTest(){}

    public MethodArgsTest(String name, String age, MethodArgsTest test) {
        this.age = age;
        this.test = test;
    }

    public static void test(MethodArgsTest tests) {
        if(tests == null) {
            tests = new MethodArgsTest("asdf", "dafda", null);
        }
    }

    public static void main(String[] args) {
        MethodArgsTest methodArgsTest = null;
        test(methodArgsTest);
        System.out.println(test);
    }

}
