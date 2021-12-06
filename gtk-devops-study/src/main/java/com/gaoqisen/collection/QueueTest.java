package com.gaoqisen.collection;

import com.alibaba.fastjson.JSONObject;
import com.gaoqisen.collection.other.MyTask;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class QueueTest {

    public static void main(String[] args) throws InterruptedException {
        // 双端队列
        System.out.println("ArrayDeque-----");
        arrayDeque();

        // 双端阻塞队列
        System.out.println("LinkedBlockingDeque-----");
        linkedBlockingDeque();

        // 链表阻塞队列， 使用ReentrantLock和Condition来保证生产和消费的同步
        System.out.println("LinkedBlockingQueue-----");
        linkedBlockingQueue();

        // 数组阻塞队列
        System.out.println("ArrayBlockingQueue-----");
        arrayBlockingQueue();

        // 优先级队列
        System.out.println("PriorityQueue------");
        priorityQueue();

        // 优先级阻塞队列
        System.out.println("PriorityBlockingQueue------");
        priorityBlockingQueue();

        // 同步队列（线程之间传值，只能传一个）
        System.out.println("SynchronousQueue-----");
        synchronousQueue();

        // 链表转换队列（线程之间传值，可以传多个）
        System.out.println("LinkedTransferQueue-----");
        linkedTransferQueue();

        // 延迟队列
        System.out.println("DelayQueue------");
        delayQueue();
    }

    private static void delayQueue() throws InterruptedException {
        DelayQueue<MyTask> delayQueue = new DelayQueue<>();
        long now = System.currentTimeMillis();
        delayQueue.add(new MyTask("t1", now + 1000));
        delayQueue.add(new MyTask("t2", now + 100));
        delayQueue.add(new MyTask("t3", now + 2000));
        delayQueue.add(new MyTask("t4", now + 6000));

        System.out.println(JSONObject.toJSONString(delayQueue));
        for (int i = 0; i < 4; i++) {
            System.out.println(delayQueue.take().toString());
        }
    }

    private static void linkedTransferQueue() throws InterruptedException {
        LinkedTransferQueue<String> linkedTransferQueue = new LinkedTransferQueue<>();
        new Thread(() -> {
            try {
                System.out.println("LinkedTransferQueue" + linkedTransferQueue.take());
                System.out.println("LinkedTransferQueue" + linkedTransferQueue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        linkedTransferQueue.transfer("123");
       // linkedTransferQueue.transfer("456");
    }

    private static void synchronousQueue() throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println("synchronousQueue" + synchronousQueue.take());
                System.out.println("synchronousQueue" + synchronousQueue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        synchronousQueue.put("123456");
        synchronousQueue.put("789");
    }

    private static void priorityBlockingQueue() throws InterruptedException {
        PriorityBlockingQueue<String> priorityBlockingQueue = new PriorityBlockingQueue<>(8);
        priorityBlockingQueue.put("3");
        priorityBlockingQueue.put("1");
        priorityBlockingQueue.put("2");
        priorityBlockingQueue.put("4");
        for (int i = 0; i < 4; i++) {
            // 取出数据，如果没有数据则进行阻塞
            System.out.println(priorityBlockingQueue.take());
        }
    }

    private static void priorityQueue() {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        priorityQueue.add("3");
        priorityQueue.add("1");
        priorityQueue.add("2");
        System.out.println("PriorityQueue: " + JSONObject.toJSONString(priorityQueue));
        for (int i = 0; i < 3; i++) {
            System.out.println(priorityQueue.poll());
        }
    }

    private static void arrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(6);
        arrayBlockingQueue.add("1");
        arrayBlockingQueue.add("2");
        arrayBlockingQueue.add("3");
        arrayBlockingQueue.offer("4");
        arrayBlockingQueue.put("5");

        System.out.println("poll: " + arrayBlockingQueue.poll());
        System.out.println(JSONObject.toJSONString(arrayBlockingQueue));
        System.out.println("peek: " + arrayBlockingQueue.peek());
        System.out.println(JSONObject.toJSONString(arrayBlockingQueue));
    }

    private static void linkedBlockingQueue() throws InterruptedException {
        LinkedBlockingQueue<String> linkBlockingQueue = new LinkedBlockingQueue<>(6);
        linkBlockingQueue.add("1");
        linkBlockingQueue.add("2");
        linkBlockingQueue.add("3");
        linkBlockingQueue.offer("4");
        linkBlockingQueue.put("5");

        System.out.println("poll: " + linkBlockingQueue.poll());
        System.out.println(JSONObject.toJSONString(linkBlockingQueue));
        System.out.println("peek: " + linkBlockingQueue.peek());
        System.out.println(JSONObject.toJSONString(linkBlockingQueue));
    }

    private static void linkedBlockingDeque() throws InterruptedException {
        LinkedBlockingDeque<String> linkBlockingDeque = new LinkedBlockingDeque<>(6);
        linkBlockingDeque.add("1");
        linkBlockingDeque.add("2");
        linkBlockingDeque.add("3");
        // 如果有可用空间则添加数据
        linkBlockingDeque.offer("4");
        // 如果空间不够则进行阻塞
        linkBlockingDeque.put("5");

        System.out.println("pop: " + linkBlockingDeque.pop());
        System.out.println("poll: " + linkBlockingDeque.poll());

        System.out.println(JSONObject.toJSONString(linkBlockingDeque));
        System.out.println("peek: " + linkBlockingDeque.peek());
        System.out.println(JSONObject.toJSONString(linkBlockingDeque));
    }

    private static void arrayDeque() {
        Deque<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.add("1");
        arrayDeque.add("2");
        arrayDeque.add("3");
        arrayDeque.addLast("4");
        arrayDeque.addFirst("0");
        arrayDeque.addFirst("-1");
        System.out.println("poll: " + arrayDeque.poll());
        System.out.println(JSONObject.toJSONString(arrayDeque));
        System.out.println("peek: " + arrayDeque.peek());
        System.out.println(JSONObject.toJSONString(arrayDeque));
    }
}

