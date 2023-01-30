package com.gqs.algorithm.class007;

public class T001_RecursiveTraversalBT {

    public static void main(String[] args) {
        Node head = new Node("A");
        head.left = new Node("B");
        head.right = new Node("C");
        head.left.left = new Node("D");
        head.left.right = new Node("E");
        head.right.left = new Node("F");

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");

    }

    // 先序遍历打印
    public static void pre(Node node) {
        if(node == null) {
            return;
        }
        System.out.print(node.val);
        pre(node.left);
        pre(node.right);
    }

    // 中序遍历打印
    public static void in(Node node) {
        if(node == null) {
            return;
        }
        in(node.left);
        System.out.print(node.val);
        in(node.right);
    }

    // 后序遍历
    public static void pos(Node node) {
        if(node == null) {
            return;
        }
        pos(node.left);
        pos(node.right);
        System.out.print(node.val);
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
