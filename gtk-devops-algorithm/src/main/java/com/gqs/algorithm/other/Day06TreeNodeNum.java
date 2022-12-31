package com.gqs.algorithm.other;

public class Day06TreeNodeNum {

    public static void main(String[] args) {
        TreeNode node = getTreeNode();

        int num = num(node, 3, 6);
        System.out.println(num);
    }

    static TreeNode getTreeNode() {
        TreeNode node = new TreeNode(4);
        TreeNode treeNode2 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);

        treeNode2.right = treeNode6;
        TreeNode treeNode1 = new TreeNode(3);
        node.left = treeNode1;
        node.right = treeNode2;

        return node;
    }

    /**
     * 给定二叉搜索树的根结点 root，返回值位于范围 [low, high] 之间的所有结点的值的和。
     */
    private static int num(TreeNode treeNode, int low, int high) {
        if(treeNode == null) {
            return 0;
        }
        int num = 0;

        // 如果大于最大数字，则只需要计算左边的值
        if(treeNode.val > high) {
            return num(treeNode.left, low, high);
        }

        // 如果小于最小值，则只需要计算右边的值
        if(treeNode.val < low) {
            return num(treeNode.right, low, high);
        }

        // 否则计算所有的
        num += treeNode.val;
        num += num(treeNode.right, low, high);
        num += num(treeNode.left, low, high);
        return num;
    }

}
