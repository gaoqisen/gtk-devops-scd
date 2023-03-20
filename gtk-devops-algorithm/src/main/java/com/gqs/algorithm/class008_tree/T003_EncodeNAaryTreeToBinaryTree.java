package com.gqs.algorithm.class008_tree;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
public class T003_EncodeNAaryTreeToBinaryTree {

    // 将多叉数转尾二叉树
    public static TreeNode encode(Node node) {
        if(node == null) {
            return null;
        }

        TreeNode treeNode = new TreeNode(node.val);
        treeNode.left = en(node.children);
        return treeNode;
    }

    public static TreeNode en(List<Node> nodes) {
        if(nodes == null || nodes.isEmpty()) {
            return null;
        }
        TreeNode head = null, current = null;
        for (Node node : nodes) {
            TreeNode treeNode = new TreeNode(node.val);
            // 记录头节点用于返回
            if(head == null) {
                head = treeNode;
            } else {
                // 将多叉节点一直往右放
                current.right = treeNode;
            }
            current = treeNode;
            // 将子多节点放到左边
            current.left = en(node.children);
        }
        return head;
    }

    // 将二叉树转为多叉树
    public static Node decode(TreeNode treeNode) {
        if(treeNode == null) {
            return null;
        }
        return new Node(treeNode.val, de(treeNode.left));
    }

    private static List<Node> de(TreeNode treeNode) {
        if(treeNode == null) {
            return null;
        }
        List<Node> list = new ArrayList<>();
        while (treeNode != null) {
            // 创建节点，children就是所有的左子节点
            Node current = new Node(treeNode.val, de(treeNode.left));
            // 将所有的左节点放到list里面
            list.add(current);
            // 处理下一个节点
            treeNode = treeNode.right;
        }
        return list;
    }

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
