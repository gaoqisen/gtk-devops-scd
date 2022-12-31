package com.gaoqisen.test;

public class CreateClassProcess {

    public static void main(String[] args) {
        Dog dog = new Dog();
    }

}


class Dog extends Animal{

    Dog(){
        System.out.println("子类的构造方法");
    }

    static {
        System.out.println("子类的静态代码块");
    }

}

class Animal{

    Animal() {
        System.out.println("父类的构造方法");
    }

    static {
        System.out.println("父类的静态代码块");
    }

}