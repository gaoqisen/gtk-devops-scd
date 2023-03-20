package com.gqs.algorithm.class009_tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 是否完全二叉树
 */
public class T001_IsComplete {


    /**
     * 判断是否为完全二叉树，非递归版
     */
    public static boolean isComplete(Node node) {
        if(node == null) {
            return false;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        boolean exist = false;
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            Node l = poll.left;
            Node r = poll.right;
            // 左孩子为空，右孩子不为空则不是
            if(l == null && r != null) {
                return false;
            }

            // 第二次遇到还有null的子节点
            if(exist && (l != null || r != null)) {
                return false;
            }

            if(l != null) {
                queue.add(l);
            }
            if(r != null) {
                queue.add(r);
            }
            // 第一次遇到没有子节点
            if(l == null || r == null) {
                exist = true;
            }
        }
        return true;
    }


    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

}
