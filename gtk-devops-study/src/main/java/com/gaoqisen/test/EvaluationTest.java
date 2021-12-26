package com.gaoqisen.test;

import istio.v1.auth.Ca;

public class EvaluationTest {


    public static void main(String[] args) {
        Cat c = new Cat();
        evaluation(c);
        System.out.println(c);

        int a = 0;
        evaluationBasic(a);
        System.out.println(a);
    }

    private static void evaluation(Cat cat) {
        Cat c = new Cat();
        cat.setName("789");
        cat.setAge("87");
    }

    private static void evaluationBasic(int a) {
        a = 5;
    }
}


class Cat{

    private String name;

    private String age;

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}