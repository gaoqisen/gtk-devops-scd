package com.gqs.algorithm.class009_tree;


// 在线测试链接 : https://leetcode.com/problems/largest-bst-subtree
public class T006_LargestBstSubtree {

    public static int getLargestBSTSubtree(TreeNode node) {
        if(node == null) {
            return 0;
        }
        return process(node).maxSubtreeSize;
    }

    public static Info process(TreeNode node) {
        if(node == null) {
            return null;
        }
        Info l = process(node.left);
        Info r = process(node.right);
        int allSize = 1;
        int max = node.val;
        int min = node.val;

        // 左节点不为空，则获取所有节点数量、最大值、最小值
        if(l != null){
            allSize += l.allSize;
            max = Math.max(max, l.max);
            min = Math.min(min, l.min);
        }

        // 右节点不为空，则获取所有节点数量、最大值、最小值
        if(r != null) {
            allSize += r.allSize;
            max = Math.max(max, r.max);
            min = Math.min(min, r.min);
        }

        // 获取左节点是否为搜索树、左节点最大搜索树节点数
        int p1 = -1;
        boolean leftBST = true;
        if(l != null){
            p1 = l.maxSubtreeSize;
            leftBST = l.maxSubtreeSize == l.allSize;
        }

        // 获取右节点是否为搜索树、右节点最大搜索树节点数
        int p2 = -1;
        boolean rightBST = true;
        if(r != null) {
            p2 = r.maxSubtreeSize;
            rightBST = r.maxSubtreeSize == r.allSize;
        }

        // 如果左右两边都是搜索树
        int p3 = -1;
        if(leftBST && rightBST) {
            int leftMax = l == null ? 0 : l.max;
            int rightMin = r == null ? 0 : r.min;
            // 如果整颗树都是搜索二叉树，则最大搜索树的节点树=左树节点树+右树节点树+1
            if(leftMax < node.val && rightMin > node.val) {
                p3 =(r== null ? 0 : r.maxSubtreeSize) + (l == null ? 0 : l.maxSubtreeSize) + 1;
            }
        }
        return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }


    public static class Info{

        public int maxSubtreeSize;
        public int allSize;
        public int max;
        public int min;

        public Info(int maxSubtreeSize, int allSize, int max, int min) {
            this.maxSubtreeSize = maxSubtreeSize;
            this.allSize = allSize;
            this.max = max;
            this.min = min;
        }
    }

    // 提交时不要提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }
}
