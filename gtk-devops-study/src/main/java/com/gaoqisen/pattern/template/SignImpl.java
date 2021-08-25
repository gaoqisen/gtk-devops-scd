package com.gaoqisen.pattern.template;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SignImpl{


    private static final AtomicInteger THREAD = new AtomicInteger(0);

    public static void main(String[] args) {
        // 抽象方法实现
//        new PageTemplate(){
//            @Override
//            int execute(int start, int end) {
//                System.out.println("开始执行分页查询数据: " + start + ", " + end);
//                return 520;
//            }
//        }.start();

        PageTemplateHelper.init((start, end) -> {
            System.out.println(start + ", " + end);
            return 100;
        });
        // 函数式

        String[] array = new String[] { "Apple", "Orange", "Banana", "Lemon" };
        Arrays.sort(array, (s1, s2) -> {
            return s1.compareTo(s2);
        });
        System.out.println(String.join(", ", array));

        Arrays.sort(array, String::compareToIgnoreCase);

    }

}
