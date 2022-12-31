package com.gqs.algorithm.other;

import java.util.ArrayList;
import java.util.List;

public class Day05TreeNode {

    public static void main(String[] args) {
        TreeNode treeNode = Day06TreeNodeNum.getTreeNode();
        increasingBST(treeNode);
    }

    /**
     * 将给定的二叉树变成全部是左边的树
     */
    public static TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        setNode(root, list);

        TreeNode dummyNode = new TreeNode(-1);
        TreeNode currNode = dummyNode;
        for (int value : list) {
            currNode.right = new TreeNode(value);
            currNode = currNode.right;
        }
        return dummyNode.right;
    }

    public static TreeNode increasingBST1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        setNode(root, list);

        TreeNode dummyNode = new TreeNode(-1);
        TreeNode currNode = dummyNode;
        for (int value : list) {
            TreeNode right = new TreeNode(value);
            dummyNode.right = right;
        }
        return dummyNode.right;
    }

    public static void setNode(TreeNode treeNode, List<Integer> res){
        if(treeNode == null) {
            return;
        }
        setNode(treeNode.left, res);
        res.add(treeNode.val);
        setNode(treeNode.right, res);
    }


}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}