package com.gqs.algorithm.class009;

public class T002_IsBalanced {


    public static boolean isBalanced1(Node head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


    // 判断是否为平衡二叉树
    public static boolean isBalanced(Node node) {
        return process(node).isBalanced;
    }

    public static Info process(Node node) {
        if(node == null) {
            return new Info(true, 0);
        }
        Info l = process(node.left);
        Info r = process(node.right);

        // 计算当前层数
        int max = Math.max(l.height, r.height) + 1;

        // 其中一个子节点不是平衡二叉树则当前数不是平衡二叉树
        if(!l.isBalanced || !r.isBalanced) {
            return new Info(false, max);
        }
        // 左右子节点高度相差超过1
        if(Math.abs(l.height - r.height) > 1) {
            return new Info(false, max);
        }
        return new Info(true, max);
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static class Info{

        public int height;
        public boolean isBalanced;

        public Info(boolean isBalanced, int height) {
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }

}
