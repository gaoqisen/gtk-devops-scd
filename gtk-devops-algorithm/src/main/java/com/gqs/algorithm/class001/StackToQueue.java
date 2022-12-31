package com.gqs.algorithm.class001;

import java.util.Stack;

public class StackToQueue {

    private Stack<Integer> stackPush;

    private Stack<Integer> stackPop;

    public StackToQueue() {
        stackPop = new Stack<>();
        stackPush = new Stack<>();
    }

    private void pushToPop() {
        if(stackPop.isEmpty()) {
            while (!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
    }


    public void push(int val) {
        stackPush.push(val);
        pushToPop();
    }

    private int pop() {
        Integer pop = stackPop.pop();
        pushToPop();
        return pop;
    }

    public static void main(String[] args) {
        StackToQueue queue = new StackToQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        queue.push(4);
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }


}
