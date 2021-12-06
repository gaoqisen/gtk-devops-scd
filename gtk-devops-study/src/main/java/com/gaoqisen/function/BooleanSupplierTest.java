package com.gaoqisen.function;

import java.util.function.BooleanSupplier;

public class BooleanSupplierTest {

    public static void main(String[] args) {

        // 在通用的test方法中执行一段返回boolean类型的函数
        test(new IfBoolean());

        // 函数表达式处理
        test(() -> {
            return true;
        });

    }

    private static void test(BooleanSupplier booleanSupplier){

        System.out.println("处理业务逻辑");
        if(booleanSupplier.getAsBoolean()) {
            System.out.println("处理成功的业务逻辑");
        } else {
            System.out.println("处理失败的业务逻辑");
        }

    }


    static class IfBoolean implements BooleanSupplier{


        @Override
        public boolean getAsBoolean() {
            System.out.println("处理通用的boolean判断逻辑");
            return false;
        }
    }

}
