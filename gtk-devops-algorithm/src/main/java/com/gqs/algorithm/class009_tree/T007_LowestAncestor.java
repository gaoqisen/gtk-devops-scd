package com.gqs.algorithm.class009_tree;

import com.gqs.algorithm.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// 给定节点以及a和b获取a和b的汇聚节点
public class T007_LowestAncestor {

    public static Node findLowestAncestor(Node head, Node a, Node b) {
        if(head == null) {
            return null;
        }
        return process(head, a, b).ans;
    }

    private static Info process(Node head, Node a, Node b) {
        if(head == null) {
            return new Info(false, false, null);
        }
        Info l = process(head.left, a, b);
        Info r = process(head.right, a, b);
        // 头节点是a 或者 左右节点找到a
        boolean findA = (head == a) || l.findA || r.findA;
        // 头节点是b 或者 左右节点找到b
        boolean findB = (head == b) || l.findB || r.findB;
        Node ans = null;
        if(l.ans != null) {
            ans = l.ans;
        } else if(r.ans != null) {
            ans = r.ans;
        } else {
            // 左右都没有找到汇聚节点，但是左边和右边都找到了值，则头节点就是汇聚节点
            if(findA && findB) {
                ans = head;
            }
        }
        return new Info(findA, findB, ans);
    }

    private static class Info{

        private boolean findA;
        private boolean findB;
        private Node ans;

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
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

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != findLowestAncestor(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
