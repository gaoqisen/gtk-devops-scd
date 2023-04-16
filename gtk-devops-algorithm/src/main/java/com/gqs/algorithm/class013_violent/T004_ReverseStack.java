package com.gqs.algorithm.class013_violent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Stack;

public class T004_ReverseStack {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(2);
        stack.push(1);

        System.out.println(JSONObject.toJSONString(stack));

        reverse(stack);

        System.out.println(JSONObject.toJSONString(stack));

    }

    // 逆序栈
    public static void reverse(Stack<Integer> stack) {
        // 如果栈空了就结束
        if(stack.isEmpty()) {
            return;
        }
        // 截取最后面的栈元素
        int i = splitLastElement(stack);
        // 递归处理处理剩余的元素
        reverse(stack);
        // 将截取的最后元素放入栈中
        stack.push(i);
    }

    // 截取栈最后的元素
    private static int splitLastElement(Stack<Integer> stack) {
        Integer pop = stack.pop();
        if(stack.isEmpty()) {
            return pop;
        }
        // 递归获取最后一个元素
        int last = splitLastElement(stack);
        // 将之前获取的元素放入栈
        stack.push(pop);
        return last;
    }


}
