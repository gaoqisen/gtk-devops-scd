package com.gqs.algorithm.class001_queue_stack;

import java.util.LinkedList;
import java.util.Queue;

public class T001_QueueToStack {

    private Queue<Integer> push;

    private Queue<Integer> help;

    public T001_QueueToStack() {
        this.push = new LinkedList<>();
        this.help = new LinkedList<>();
    }

    public void push(int val) {
        push.offer(val);
    }

    public int pop() {
        while (push.size() > 1) {
            help.offer(push.poll());
        }
        int poll = push.poll();
        Queue<Integer> temp = push;
        this.push = this.help;
        this.help = temp;
        return poll;
    }

    public static void main(String[] args) {
        T001_QueueToStack queue = new T001_QueueToStack();
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
