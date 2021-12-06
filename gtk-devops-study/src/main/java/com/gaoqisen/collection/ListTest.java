package com.gaoqisen.collection;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        /**
         * 1. add时候是使用Arrays.copyOf进行扩容的
         * 1. 获取数据直接获取，没有加锁
         */
        copyOnWriteArrayList.add("1");
        copyOnWriteArrayList.add("2");
        copyOnWriteArrayList.add("3");
        for (int i = 0; i < copyOnWriteArrayList.size(); i++) {
            // 获取数据直接获取，没有加锁
            System.out.println(copyOnWriteArrayList.get(i));
        }

        /**
         * 1. set/get场景多
         * 2. 利用Arrays.copyOf去自动增长数组
         * 3. get直接通过下标去取数据
         */
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.set(0, "2");
        System.out.println(arrayList.get(0));

        /**
         * 1. add/remove场景多
         * 2. 添加数据的时候，直接将数据添加到最后一个节点
         * 3. 获取数据的时候通过size >> 1后，判断是从前往后，还是从后往前
         */
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
        System.out.println(linkedList.get(2));

        /**
         * 方法上面添加了synchronized
         */
        Vector<String> vector = new Vector<>();
        vector.add("1");
        System.out.println("vector: " + JSONObject.toJSONString(vector));

        /**
         * 继承Vector添加了一些方法
         */
        Stack<String> stack = new Stack<>();
        stack.add("1");
        System.out.println("stack: " + JSONObject.toJSONString(stack));
    }

}
