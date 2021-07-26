package com.gaoqisen.basic;

public class AbstractClassTest {

    private static Animal animal;

    public static void main(String[] args) {
        System.out.println(animal.getAge());
        System.out.println((5-1) / 500 + 1);
    }

}

abstract class Animal{

    public String getName() {
        return "动物名称";
    }

    public abstract String getAge();
}

class Dog extends Animal{

    @Override
    public String getAge() {
        return "19";
    }
}