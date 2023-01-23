package com.gqs.algorithm.class001;

import com.alibaba.fastjson.JSONObject;

/**
 * 反转数组
 */
public class T005_ReverseLink {

    public static void main(String[] args) {
        Node node = Node.build(1);
        node.setNext(2).setNext(2).setNext(3);
        System.out.println(JSONObject.toJSONString(node));
        System.out.println(JSONObject.toJSONString(reverse(node)));
    }

    // 反转链表
    public static Node reverse(Node node) {
        Node pre = null;
        Node next = null;
        while (node != null) {
            // 将next指针往下调
            next = node.next;
            // 将下一个指针指向上一个节点
            node.next = pre;
            // 将上一个节点指向当前节点
            pre = node;
            // 当前节点指向下一个节点
            node = next;
        }
        return pre;
    }


    public static class Node{

        public int val;

        public Node next;

        public Node setNext(int val) {
            Node build = build(val);
            this.next = build;
            return build;
        }

        public static Node build(int val) {
            return new Node(val);
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(){}
    }

}
