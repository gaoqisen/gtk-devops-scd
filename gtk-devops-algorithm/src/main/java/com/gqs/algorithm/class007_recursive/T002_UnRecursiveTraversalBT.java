package com.gqs.algorithm.class007_recursive;

import java.util.Stack;

public class T002_UnRecursiveTraversalBT {

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
        Stack<Node> stack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            System.out.print(pop.val);

            if(pop.right != null) {
                stack.push(pop.right);
            }

            if(pop.left != null) {
                stack.push(pop.left);
            }
        }
    }

    // 中序遍历打印
    public static void in(Node node) {
        if(node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || node != null) {
            if(node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                System.out.print(node.val);
                node = node.right;
            }
        }
    }

    // 后序遍历打印(和先序不同的就是先左在右，之后逆序输出即可)
    public static void pos(Node node) {
        if(node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Stack<Node> pos = new Stack<>();

        stack.add(node);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            pos.push(pop);
            if(pop.left != null) {
                stack.push(pop.left);
            }
            if(pop.right != null) {
                stack.push(pop.right);
            }
        }

        while (!pos.isEmpty()) {
            Node pop = pos.pop();
            System.out.print(pop.val);
        }
    }

    // 用一个栈栈实现后序打印
    public static void pos1(Node node) {

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
