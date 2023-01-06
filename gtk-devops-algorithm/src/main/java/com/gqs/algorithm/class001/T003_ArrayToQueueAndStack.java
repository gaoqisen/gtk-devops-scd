package com.gqs.algorithm.class001;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 数组实现队列和栈
 */
 public class T003_ArrayToQueueAndStack {

    public static void main(String[] args) {
        System.out.println("数组实现队列");
        ArrayQueue queue = new ArrayQueue(3);
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(JSONObject.toJSONString(queue.getQueue()));
        queue.push(4);
        System.out.println(JSONObject.toJSONString(queue.getQueue()));
        System.out.println(queue.pop());
        queue.push(5);
        System.out.println(JSONObject.toJSONString(queue.getQueue()));

        System.out.println("数组实现栈");
        ArrayStack stack = new ArrayStack(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(JSONObject.toJSONString(stack.getStack()));
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(4);
        System.out.println(JSONObject.toJSONString(stack.getStack()));
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        // 循环数组队列(牺牲一个位置实现)
        CycleQueueArray queueArray = new CycleQueueArray(3);
        queueArray.push(10);
        queueArray.push(11);
        queueArray.push(9);
        queueArray.push(97);
        System.out.println(queueArray.pop());
        queueArray.push(96);
        queueArray.push(97);
        System.out.println(queueArray.pop());

    }


}

@Data
class ArrayStack{

    private int[] stack;

    private int index;

    public ArrayStack(int size){
        this.stack = new int[size];
        this.index = 0;
    }

    public void push(int val) {
        if(index == stack.length) {
            System.out.println("满了");
            return;
        }

        int temp = index;
        this.index++;
        this.stack[temp] = val;
    }

    public int pop(){
        if(this.index == 0) {
            System.out.println("没有数据了");
            return -1;
        }
        this.index--;
        return this.stack[this.index];
    }
}

// 数组队列: 先进先出
@Data
class ArrayQueue {

    private int[] queue;

    private int start;

    private int end;

    private int size;

    public ArrayQueue(int size) {
        this.start = 0;
        this.end = 0;
        this.queue = new int[size];
    }

    public void push(int val){
        if(size == queue.length) {
            System.out.println("满了");
            return;
        }
        this.queue[end] = val;
        this.end = end < queue.length-1 ? end+1 : 0;
        this.size++;
    }

    public int pop() {
        this.size--;
        this.start = start < queue.length ? start++ : 0;
        return this.queue[start];
    }

}

/**
 * 循环队列数组
 *
 * https://www.cnblogs.com/heliusKing/p/12326816.html
 */
class CycleQueueArray {

    int querySize = 0;
    int front = 0;
    int rear = 0;
    int[] query;

    public CycleQueueArray(int querySize){
        // 牺牲一个位置用于标记
        querySize++;
        this.querySize = querySize;
        query = new int[querySize];
        front = 0;
        rear = 0;
    }

    public boolean isEmpty(){
        // 当队尾和队头相等时，表明为空队列
        return rear == front;
    }

    /**
     * 判断是否为满队列（当头和尾在同一个位置时，无法区分是空数组还是满了）
     * 一般有两种做法：
     * 1、用一个标记去记录
     * 2、牺牲一个位置用来标记
     *
     * @return  true：满
     */
    public boolean isFull(){
        // 表明队尾和队头相邻 且队尾在左，队头在右
        return (rear + 1) % querySize == front;
    }

    public void push(int num){
        if (isFull()){
            System.out.println("队列已满 无法加入数据");
            return;
        }
        query[rear] = num;
        System.out.println((rear + 1) % querySize);
        rear = (rear + 1) % querySize;
        showQueue();
    }

    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("为空，不能取值");
        }
        int value =  query[front];
        front = (front + 1) % querySize;
        return value;
    }

    public void showQueue(){
        if (isEmpty()){
            System.out.println("为空，不能遍历");
        }
        for (int i = front; i < front + (rear + querySize - front) %querySize; i++){
            System.out.print(query[i] + ", ");
        }

        System.out.println("");
    }
}
