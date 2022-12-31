package com.gqs.algorithm.other;

public class DynamicProgramming {


    public static void main(String[] args) {
        System.out.println(fib(6));
    }


    private static int fib(int val) {
        if(val <= 0) {
            return 0;
        }

        if(val == 1) {
            return 1;
        }

        return fib(val - 1) + fib(val - 2);
    }

}
