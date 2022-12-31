package com.gqs.algorithm.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 波兰表达式
 */
public class D08PolandNotation {

    public static void main(String[] args) {
        // 将中缀表达式转为后缀表达式

        String expression = "1+((2+3)*4)-5";
        char[] chars = expression.toCharArray();
        List<String> list = new ArrayList<>();
        for(char c : chars) {
            list.add(c + "");
            list.add(",");
        }
        list = list.subList(0, list.size() - 1);
        System.out.println(list);

        List<String> parseSuffixExpressionList = parseSuffixExpressionList(list);
        System.out.println("parseSuffixExpressionList: " + parseSuffixExpressionList);


        // 定义逆波兰表达式的数字和符号使用空格隔开 (3 + 4) * 5 - 6
        String suffixExpression = "3 4 + 5 * 6 - ";

        // 1. 将表达式放入到arrayList中
        List<String> stringList = getListString(suffixExpression);

        // 2. 遍历list配合栈完成
        System.out.println(calculate(stringList));
    }

    public static List<String> parseSuffixExpressionList(List<String> ls) {
        Stack<String> s1 = new Stack<>();
        List<String> s2 = new ArrayList<>();

        for(String item : ls) {
            if(item.matches("\\d+")) {
                s2.add(item);
            } else if(item.equals("(")){
                s1.push(item);
            } else if(item.equals(")")) {
                while (!s1.peek().equals(")")){
                    s2.add(s1.pop());
                }
                // 将(弹出s1栈，消除小括号
                s1.pop();
            } else {
                // 当item的运算符小于等于s1栈顶运算符
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                s1.push(item);
            }
        }

        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        // 因为是存放在list中，因此按顺序输出就是逆序输出
        return s2;
    }


    public static List<String> getListString(String suffixExpression) {
        String[] split = suffixExpression.split(" ");
        return Arrays.asList(split);
    }

    public static int calculate(List<String> list) {
        Stack<String> stack = new Stack<>();
        for (String item : list) {
            if(item.matches("\\d+")) {
                stack.push(item);
            } else {
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if(item.equals("+")) {
                    res = num1 + num2;
                } else if(item.equals("-")) {
                    res = num1 - num2;
                } else if(item.equals("*")) {
                    res = num1 * num2;
                } else if(item.equals("/")) {
                    res = num1 / num2;
                }
                stack.push(res + "");
            }
        }
        return Integer.parseInt(stack.pop());
    }

}

class Operation{
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:

                break;
        }
        return result;
    }
}
