package com.gqs.algorithm.class014_dp;

// 斐波那契数列
public class T001_FibonacciSequence {


    public static int fibonacci(int n) {
        if(n == 1 || n == 2) {
            return 1;
        }
        //当n >=3时有, Fn = (Fn-1) + (Fn-2)
        return fibonacci(n -1 ) + fibonacci((n -2));
    }

}
