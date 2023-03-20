package com.gqs.algorithm.class001_queue_stack;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

class T001_PartitionAndQuickSort {

    public static void main(String[] args) {
        // 链表实现队列，先进先出
        LinkedQueueNode node = new LinkedQueueNode();
        node.push("1");
        node.push("2");
        node.push("3");
        System.out.println("node: " + JSONObject.toJSONString(node.getHead()));
        Node pop = node.pop();
        System.out.println("pop: " + JSONObject.toJSONString(pop));

        System.out.println("链表实现栈");
        LinkedStack linkedStack = new LinkedStack();
        linkedStack.push("1");
        linkedStack.push("2");
        linkedStack.push("3");

        System.out.println(linkedStack.pop().getName());
        System.out.println(linkedStack.pop().getName());
        linkedStack.push("4");
        System.out.println(linkedStack.pop().getName());
        System.out.println(linkedStack.pop().getName());


    }
}

class LinkedStack{

    private Node node;

    public void push(String name) {
        Node node = new Node(name);
        if(this.node == null){
            this.node = node;
            return;
        }

        node.setNext(this.node);
        this.node = node;
    }

    public Node pop() {
        if(this.node == null) {
            return null;
        }
        Node next = this.node;

        this.node = next.getNext();
        return next;
    }


}



@Data
class LinkedQueueNode {

    private Node head;

    private Node tail;

    private Integer size = 0;

    public void push(String name) {
        Node node = new Node(name);
        if(this.head == null) {
           this.head = node;
        }
        if(this.tail != null) {
            this.tail.setNext(node);
        }
        this.tail = node;
        this.size++;
    }

    public Node pop() {
        Node head = this.head;
        this.head = this.head.getNext();
        head.setNext(null);
        return head;
    }
}

@Data
@ToString
class Node {

    private String name;
    private Node next;

    public Node(String name) {
        this.name = name;
    }
}
