package com.gqs.algorithm.class008_tree;

import java.util.LinkedList;
import java.util.Queue;

public class T001_LevelRecursiveBT {

    public static void main(String[] args) {
        Node head = new Node("A");
        head.left = new Node("B");
        head.right = new Node("C");
        head.left.left = new Node("D");
        head.left.right = new Node("E");
        head.right.left = new Node("F");

        level(head);
        System.out.println("========");

    }

    // 按层遍历
    public static void level(Node node) {
        if(node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            // 弹出队列里面的值打印
            Node poll = queue.poll();
            System.out.print(poll.val);

            // 队列里面的存在left｜right则放入队列中
            if(poll.left != null) {
                queue.add(poll.left);
            }

            if(poll.right != null) {
                queue.add(poll.right);
            }
        }
    }

    public static class Node{

        public String val;

        public Node left;

        public Node right;

        public Node(String val) {
            this.val = val;
        }

    }

}
