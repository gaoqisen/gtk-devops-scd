package com.gqs.algorithm.other;

public class D02ArrayQueue {

    public static void main(String[] args) {

        ArrayQueue arrayQueue = new ArrayQueue(3);
        arrayQueue.add(5);
        arrayQueue.add(6);
        arrayQueue.add(7);
        arrayQueue.show();

        int pop = arrayQueue.pop();
        System.out.println(pop);

        arrayQueue.add(1);
        arrayQueue.show();
        System.out.println(arrayQueue.headQueue());

    }
}

/**
 * 利用数组实现队列
 */
class ArrayQueue {

    /**
     * 最大容量
     */
    private int maxSize;

    /**
     * 头
     */
    private int front;

    /**
     * 尾
     */
    private int rear;

    /**
     * 存放数据
     */
    private int[] arr;

    // 创建队列的构造器

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[maxSize];
        // 指向队列头部 指向队列头的前一个位置
        this.front = -1;
        // 指向队列尾部（最后一个数据）
        this.rear = -1;
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

        rear++;
        arr[rear] = n;
    }

    public int pop() {
        if(isEmpty()) {
            throw new RuntimeException("没有数据");
        }
        // 后移
        front++;
        return arr[front];
    }

    public void show() {
        if(isEmpty()) {
            System.out.println("数组为空");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    /**
     * 查看第一个元素
     */
    public int headQueue() {
        if(isEmpty()) {
            System.out.println("数组为空");
            throw new RuntimeException("没有数据");
        }
        return arr[front + 1];

    }

}