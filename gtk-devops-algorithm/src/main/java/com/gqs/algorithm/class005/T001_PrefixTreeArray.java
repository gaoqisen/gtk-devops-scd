package com.gqs.algorithm.class005;

public class T001_PrefixTreeArray {

    public static class Tire{

        public Node root;

        public Tire() {
            this.root = new Node();
        }

        // 插入字符串
        public void insert(String str) {
            if(str == null || str.isEmpty()) {
                return;
            }

            char[] chars = str.toCharArray();
            Node node = root;
            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';
                if(node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
                node.pass++;
            }
            node.end++;
        }

        // 删除字符串
        public void del(String str) {
            if(str == null || str.isEmpty() || findCount(str) == 0) {
                return;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            node.pass--;
            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';
                if(--node.nexts[index].pass == 0) {
                    node.nexts[index] = null;
                    return;
                }
                node = node.nexts[index];
            }
            node.end--;
        }

        // 获取字符串出现了几次
        public int findCount(String str) {
            if(str == null || str.isEmpty()) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';
                if(node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }

        // 获取有多少个符合str的前缀字符串
        public int prefixCount(String str) {
            if(str == null || str.isEmpty()) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node node = root;
            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';
                if(node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }
    }

    public static class Node{

        public int pass;

        public int end;

        public Node[] nexts;

        public Node() {
            this.pass = 0;
            this.end = 0;
            // 按照26个字母作为路径
            this.nexts = new Node[26];
        }
    }

}
