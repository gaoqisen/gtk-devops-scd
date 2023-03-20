package com.gqs.algorithm.class009_tree;

import com.gqs.algorithm.Node;

import java.util.LinkedList;
import java.util.List;

public class T003_IsSearch {


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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isSearchIn(head) != isSearch(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


    public static boolean isSearchIn(Node node) {
        if(node == null) {
            return true;
        }
        List<Node> list = new LinkedList<>();
        // 利用中序将数据放到list里面
        in(node, list);
        for (int i = 1; i < list.size(); i++) {
            // 如果存在前面的数大于后面的数则非搜索树
            if(list.get(i).value <= list.get(i -1).value) {
                return false;
            }
        }
        return true;
    }

    public static void in(Node node, List<Node> list) {
        if(node == null) {
            return;
        }
        in(node.left, list);
        list.add(node);
        in(node.right, list);
    }


    public static boolean isSearch(Node node) {
        if(node == null) {
            return true;
        }
        return process(node).isSearch;
    }

    public static Info process(Node node) {
        if(node == null) {
            return null;
        }
        Info l = process(node.left);
        Info r = process(node.right);

        // 获取左右孩子的最大最小值
        int max = node.value;
        int min = node.value;
        if(l != null) {
            max = Math.max(max, l.max);
            min = Math.min(min, l.min);
        }
        if(r != null) {
            max = Math.max(max, r.max);
            min = Math.min(min, r.min);
        }

        // 左右孩子其中一个非搜索
        if((l != null && !l.isSearch) || (r != null && !r.isSearch)) {
            return new Info(false, max, min);
        }
        // 左边的值大于等于当前值 或者 右边的值小于当前值
        if((l != null && l.max >= node.value) || (r != null && r.min <= node.value)) {
            return new Info(false, max, min);
        }
        return new Info(true, max, min);
    }

    public static class Info{
        public boolean isSearch;
        public int max;
        public int min;

        public Info(boolean isSearch, int max, int min) {
            this.isSearch = isSearch;
            this.max = max;
            this.min = min;
        }
    }

}
