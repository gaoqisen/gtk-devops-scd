package com.gqs.algorithm.class008_tree;

/**
 * 获取后继节点
 */
public class T005_SuccessorNode {

    public static void main(String[] args) {
        {
            Node head = new Node(6);
            head.parent = null;
            head.left = new Node(3);
            head.left.parent = head;
            head.left.left = new Node(1);
            head.left.left.parent = head.left;
            head.left.left.right = new Node(2);
            head.left.left.right.parent = head.left.left;
            head.left.right = new Node(4);
            head.left.right.parent = head.left;
            head.left.right.right = new Node(5);
            head.left.right.right.parent = head.left.right;
            head.right = new Node(9);
            head.right.parent = head;
            head.right.left = new Node(8);
            head.right.left.parent = head.right;
            head.right.left.left = new Node(7);
            head.right.left.left.parent = head.right.left;
            head.right.right = new Node(10);
            head.right.right.parent = head.right;

            Node test = head.left.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left.left.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.left.right.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right.left.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right.left;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right;
            System.out.println(test.value + " next: " + getSuccessorNode(test).value);
            test = head.right.right; // 10's next is null
            System.out.println(test.value + " next: " + getSuccessorNode(test));
        }
    }


    public static class Node{

        private int value;

        private Node left;

        private Node right;

        private Node parent;

        public Node(int str) {
            this.value = str;
        }
    }

    public static Node getSuccessorNode(Node node) {
        if(node == null) {
            return node;
        }

        if(node.right != null) {
            Node right = node.right;
            while (right.left != null) {
                right = right.left;
            }
            return right;
        } else {
            Node parent = node.parent;
            // 当前节点是其父节点的右孩子
            while (parent != null && parent.right == node) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }
}
