package com.gaoqisen.function;

import java.util.function.UnaryOperator;

public class UnaryOperatorTest {

    public static void main(String[] args) {

        UnaryOperator<Integer> unaryOperator = (a) -> {
            return a - 1;
        };
        test(8, unaryOperator);

    }

    private static void test(Integer a, UnaryOperator<Integer> unaryOperator) {
        System.out.println("业务逻辑");
        Integer apply = unaryOperator.apply(a);
        System.out.println("业务逻辑");
    }

}
