package com.gqs.algorithm.class006_node;

import java.util.HashMap;
import java.util.Map;

/**
 * 拷贝随机链表
 * https://leetcode.com/problems/copy-list-with-random-pointer/
 */
public class T004_CopyRandomLinkList {

    public static void main(String[] args) {
        Node node = Node.build(1);
        node.setNext(2).setNext(2).setNext(3).setNext(2).setNext(2).setNext(1).setNext(6);

    }

    /**
     * 通过hashMap复制随机节点
     */
    public static Node copyWithHashMap(Node head) {
        // 将新旧节点都放到map容器中
        Map<Node, Node> map = new HashMap<>();
        Node index = head;
        while (index != null) {
            map.put(index, new Node(index.val));
            index = index.next;
        }

        // 将容器中新节点的下一个和随机元素指向新节点的位置
        index = head;
        while (index != null) {
            map.get(index).next = map.get(index.next);
            map.get(index).random = map.get(index.random);

            index = index.next;
        }

        return map.get(head);
    }

    public static Node copyRandomNode(Node head) {
        if(head == null) {
            return null;
        }
        Node current = head, next = null;
        while (current != null) {
            next = current.next;
            Node node = new Node(current.val);
            node.next = next;
            current.next = node;
            current = next;
        }

        current = head;
        Node copy = null;
        while (current != null) {
            copy = current.next;
            copy.random = current.random == null ? null : current.random.next;
            current = current.next.next;
        }

        Node res = head.next;
        current = head;
        while (current != null) {
            next = current.next.next;
            copy = current.next;
            copy.next = next == null ? null : next.next;
            current.next = next;
            current = next;
        }
        return res;
    }


    public static class Node{

        public int val;

        public Node next;

        public Node random;


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
