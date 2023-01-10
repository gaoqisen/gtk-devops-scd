package com.gqs.algorithm.class005;

import java.util.HashMap;

public class T002_PrefixTreeHash {

    private Node root;

    public T002_PrefixTreeHash() {
        this.root = new Node();
    }

    public void insert(String str) {
        if(str == null || str.isEmpty()) {
            return;
        }
        char[] chars = str.toCharArray();
        Node node = root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i];
            if(!node.hashMap.containsKey(index)){
                node.hashMap.put(index, new Node());
            }
            node.hashMap.get(index).pass++;
            node = node.hashMap.get(index);
        }
        node.end++;
    }

    public void del(String str){
        if(findCount(str) == 0) {
            return;
        }

        if(str == null || str.isEmpty()) {
            return;
        }
        char[] chars = str.toCharArray();
        Node node = root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i];
            if(--node.hashMap.get(index).pass == 0) {
                node.hashMap.remove(index);
                return;
            }
            node = node.hashMap.get(index);
        }
        node.end--;
    }

    public int findCount(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        char[] chars = str.toCharArray();
        Node node = root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i];
            if(node.hashMap.get(index) == null){
                return 0;
            }
            node = node.hashMap.get(index);
        }
        return node.end;
    }

    public int prefixCount(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        char[] chars = str.toCharArray();
        Node node = root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i];
            if(node.hashMap.get(index) == null) {
                return 0;
            }
            node = node.hashMap.get(index);
        }
        return node.pass;
    }

    public static class Node{

        public int pass;

        public int end;

        public HashMap<Integer, Node> hashMap;

        public Node() {
            this.pass = 0;
            this.end = 0;
            this.hashMap = new HashMap<>();
        }

    }
}
