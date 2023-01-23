package com.gqs.algorithm.class006;

public class T001_FastSlowPointer {

    public static void main(String[] args) {
        FastSlowPointer fastSlowPointer = new FastSlowPointer();
        fastSlowPointer.push(new Node(2));
        fastSlowPointer.push(new Node(1));
        fastSlowPointer.push(new Node(6));
        fastSlowPointer.push(new Node(5));
        fastSlowPointer.push(new Node(8));


        System.out.println(fastSlowPointer.getMiddle4().val);
    }

    public static class Node{

        public int val;

        public Node next;

        public Node(int val) {
            this.val = val;
        }

        public Node(){}
    }


    public static class FastSlowPointer{

        public Node root;
        public Node current;

        public FastSlowPointer() {
            this.root = new Node();
            this.current = root;

        }

        public void push(Node node) {
            this.current.next = node;
            this.current = node;
        }

        // 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
        public Node getMiddle1() {
            if(this.root == null || this.root.next == null || this.root.next.next == null) {
                return null;
            }
            Node fast = this.root.next.next.next;
            Node slow = this.root.next.next;
            while ( fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }

        // 2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点
        public Node getMiddle2() {
            if(this.root == null || this.root.next == null || this.root.next.next == null) {
                return null;
            }
            Node fast = this.root.next.next;
            Node slow = this.root.next.next;
            while ( fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }

        // 3) 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
        public Node getMiddle3() {
            if(this.root == null || this.root.next == null || this.root.next.next == null) {
                return null;
            }
            Node fast = this.root.next.next.next;
            Node slow = this.root.next;
            while ( fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }

        // 4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
        public Node getMiddle4() {
            if(this.root == null || this.root.next == null || this.root.next.next == null) {
                return null;
            }
            Node fast = this.root.next.next;
            Node slow = this.root.next;
            while ( fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }
    }

}
