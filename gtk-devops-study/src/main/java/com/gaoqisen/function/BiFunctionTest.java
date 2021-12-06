package com.gaoqisen.function;

import java.util.function.BiFunction;

public class BiFunctionTest {

    public static void main(String[] args) {
        // 入参是一个对象出参是一个对象时
        BiFunction<Integer, String, Double> fun = (a, b) -> {
            return Double.valueOf(1.0);
        };
        test(9,"1",  fun);


        test(8, "2", (a, b) -> {
            return Double.valueOf(2.0);
        });

    }

    private static void test(Integer a, String b, BiFunction<Integer, String, Double> function) {
        System.out.println("业务逻辑");
        Double apply = function.apply(a, b);
        System.out.println(apply);
        System.out.println("业务逻辑");
    }

}
