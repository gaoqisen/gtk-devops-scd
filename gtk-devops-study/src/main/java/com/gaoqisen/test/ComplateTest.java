package com.gaoqisen.test;

public class ComplateTest {

    public static void main(String[] args) {

        String a = "01000";
        String b = "10000";

        int c = Integer.parseInt(a) | Integer.parseInt(b);
        System.out.println(c);

    }

}
