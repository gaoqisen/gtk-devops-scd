package com.gaoqisen.test;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Fanxing {

    @Test
    public void typeWipe() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Integer> list = new ArrayList<>();

        // 这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer
        list.add(1);
        list.getClass().getMethod("add", Object.class).invoke(list, "asd");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void compute() {
        System.out.println(3*0.1 == 0.3);
    }


    @Test
    public void byteTest() {
        System.out.println(01000 | 01100);
    }

}
