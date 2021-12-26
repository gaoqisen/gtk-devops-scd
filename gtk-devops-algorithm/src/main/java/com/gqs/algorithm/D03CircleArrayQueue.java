package com.gqs.algorithm;


public class D03CircleArrayQueue {

    public static void main(String[] args) {

        CircleArrayQueue circleArrayQueue = new CircleArrayQueue(4);
        circleArrayQueue.add(5);
        circleArrayQueue.add(6);
        circleArrayQueue.add(7);
        circleArrayQueue.show();

        int pop = circleArrayQueue.pop();
        System.out.println(pop);

        circleArrayQueue.add(1);
        circleArrayQueue.show();
        System.out.println(circleArrayQueue.headQueue());


        System.out.println(2%3);
    }
}

/**
 * 利用数组实现队列
 */
class CircleArrayQueue {

    /**
     * 最大容量
     */
    private int maxSize;

    /**
     * 头：指向头的位置
     */
    private int front;

    /**
     * 尾: 指向列表尾的后一位
     */
    private int rear;

    /**
     * 存放数据
     */
    private int[] arr;

    // 创建队列的构造器

    public CircleArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[maxSize];
    }

    /**
     * 判断对象是否满
     */
    public  boolean isFull() {
        return rear == maxSize - 1;
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public void add(int n) {
        if(isFull()) {
            System.out.println("队列满");
            return;
        }

        arr[rear] = n;
        rear = (rear + 1) % maxSize;
    }

    public int pop() {
        if(isEmpty()) {
            throw new RuntimeException("没有数据");
        }

        int val = arr[front];
        front = (front + 1) % maxSize;
        return val;
    }

    public void show() {
        if(isEmpty()) {
            System.out.println("数组为空");
            return;
        }
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    /**
     * 查看第一个元素
     */
    public int headQueue() {
        if(isEmpty()) {
            System.out.println("数组为空");
            throw new RuntimeException("没有数据");
        }
        return arr[front];

    }

}