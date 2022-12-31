package com.gqs.algorithm.other;

/**
 * 利用链表实现栈(中缀表达式)
 */
public class D07ArrayStack {

    /**
     * 思路
     *
     * 1. 创建两个栈，一个栈保存数字，一个栈保存符号
     * 2. 遍历表达式，如果是数字则入数栈，如果是符号则入符号栈。通过符号优先级进行计算，
     *      遇到高优先级的符号则将数栈的前两个数字取出计算。依次遍历计算后的最后数字就是计算和
     */
    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(5);
        arrayStack.push(6);
        arrayStack.push(9);
        arrayStack.push(2);
        arrayStack.push(1);
        arrayStack.push(7);

        arrayStack.list();
        System.out.println(arrayStack.pop());
        arrayStack.list();

        String expression = "30+2*7-2";
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);

        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';
        String keepNum ="";
        while (true) {
            ch = expression.substring(index, index+1).charAt(0);
            if(operStack.isOper(ch)) {
                if (!operStack.isEmpty()) {
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, (char) oper);
                        numStack.push(res);
                    }
                }
                operStack.push(ch);
            } else {
                // 字符转数字。 可能是多位数字，需要在入数栈时，需要想表达式的index后在看一位。符号则入栈
                // numStack.push(ch - 48);
                keepNum += ch;
                if(index == expression.length()-1) {
                    numStack.push(Integer.valueOf(keepNum));
                } else {
                    if(operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))){
                        numStack.push(Integer.valueOf(keepNum));
                        keepNum = "";
                    }
                }

            }
            index++;
            if(index >= expression.length()) {
                break;
            }
        }

        while (true) {
            if(operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, (char) oper);
            numStack.push(res);
        }

        System.out.println("结果"+ numStack.pop());

    }

}

class ArrayStack {

    private int maxSize;

    private int[] stack;

    // 表示栈顶，初始化为-1
    private int top = -1;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    public int peek() {
        return stack[top];
    }

    public void push(int val) {
        if(isFull()) {
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = val;
    }

    public int pop() {
        if(isEmpty()) {
            throw new RuntimeException("栈空");
        }

        int value = stack[top];
        top--;
        return value;
    }

    public void list() {
        if(isEmpty()) {
            System.out.println("没有数据");
            return;
        }

        for (int i = top; i >= 0 ; i--) {
            System.out.println("数据" + stack[i]);
        }
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }


    public int priority(int oper) {
        if(oper == '*' || oper == '/') {
            return 1;
        } else if(oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    public int cal(int num1, int num2, char oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
        }
        return res;
    }

}
