package com.gqs.algorithm.class_018_KMP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T003_TreeEqual {

    public static boolean containsTree1(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        if (isSameValueStructure(big, small)) {
            return true;
        }
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
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
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = isContains(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }

    /**
     * 判断两个二叉树是否是包含关系
     *
     * @param node1 主树
     * @param node2 被包含的树
     * @return 是否包含
     */
    public static boolean isContains(Node node1, Node node2) {
        if(node2 == null) {
            return true;
        }
        if(node1 == null) {
            return false;
        }
        // 将树序列化
        List<String> list1 = preSerial(node1);
        List<String> list2 = preSerial(node2);
        // 通过kmp判断是否包含子串
        return getIndexOf(getArr(list1), getArr(list2)) != -1;
    }

    // 将list转化数组
    private static String[] getArr(List<String> list) {
        String[] str = new String[list.size()];
        for (int i = 0; i < str.length; i++) {
            str[i] = list.get(i);
        }
        return str;
    }

    // 先序遍历
    private static List<String> preSerial(Node node) {
        List<String> list = new ArrayList<>();
        pre(node, list);
        return list;
    }

    // 递归先序遍历
    private static void pre(Node node, List<String> list) {
        if(node == null) {
            list.add(null);
        } else {
            list.add(String.valueOf(node.value));
            pre(node.left, list);
            pre(node.right, list);
        }
    }

    // 获取两个数组是否包含并返回包含的下标
    public static int getIndexOf(String[] str1, String[] str2) {
        if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
            return -1;
        }
        int x = 0;
        int y = 0;
        int[] next = getNextArray(str2);
        while (x < str1.length && y < str2.length) {
            if (isEqual(str1[x], str2[y])) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    /// 判断两个字符串是否相等
    public static boolean isEqual(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

    // 获取next数组
    public static int[] getNextArray(String[] ms) {
        if (ms.length == 1) {
            return new int[] { -1 };
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (isEqual(ms[i - 1], ms[cn])) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

}
