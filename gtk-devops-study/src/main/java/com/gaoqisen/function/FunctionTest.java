package com.gaoqisen.function;

import java.util.function.Function;

public class FunctionTest {

    public static void main(String[] args) {
        // 入参是一个对象出参是一个对象时
        Function<Integer, String> fun = (a) -> {
            return String.valueOf(a - 1);
        };
        test(9, fun);


        test(8, (a) -> {
            return String.valueOf(a - 1);
        });

    }

    private static void test(Integer a, Function<Integer, String> function) {
        System.out.println("业务逻辑");
        String apply = function.apply(a);
        System.out.println(apply);
        System.out.println("业务逻辑");
    }

}
