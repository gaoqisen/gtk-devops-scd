package com.gqs.algorithm.class011;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集
 */
public class T001_UnionSet {


    public static void main(String[] args) {
        UnionSet<Integer> unionSet = new UnionSet<>(Arrays.asList(3,5,11,45,23,17));
        System.out.println(unionSet.findSameSet(3, 11));
        unionSet.union(3, 5);
        System.out.println(unionSet.findSameSet(3, 5));
    }
}

class UnionSet<V>{

    // 值对应自己的包装类
    private HashMap<V, Node<V>> nodes;
    // 节点于父亲节点的对应关系
    private HashMap<Node<V>, Node<V>> parents;
    // 集合中的数量
    private HashMap<Node<V>, Integer> counts;

    public UnionSet(List<V> list) {
        nodes = new HashMap<>();
        parents = new HashMap<>();
        counts = new HashMap<>();

        if(list == null || list.isEmpty()) {
            return;
        }
        for (V v : list) {
            Node<V> node = new Node<>(v);
            nodes.put(v, node);
            parents.put(node, node);
            counts.put(node, 1);
        }
    }

    // 查找是否相同的集合
    public boolean findSameSet(V a, V b){
        // 如果节点的祖先节点相等则是相同的集合
        return findAncestry(nodes.get(a)) == findAncestry(nodes.get(b));
    }

    // 合并两个节点
    public void union(V a, V b) {
        Node<V> aNode = findAncestry(nodes.get(a));
        Node<V> bNode = findAncestry(nodes.get(b));
        if(aNode == bNode) {
            return;
        }

        // 获取长度
        Integer aLength = counts.get(aNode);
        Integer bLength = counts.get(bNode);
        // 大小重定向
        Node<V> bigNode = aLength > bLength ? aNode : bNode;
        Node<V> smallNode = bigNode == bNode ? aNode : bNode;
        // 小的指向大的
        parents.put(smallNode, bigNode);
        // 大节点增加数量和删除小节点
        counts.put(bigNode, aLength + bLength);
        counts.remove(smallNode);
    }

    // 查找祖先
    private Node<V> findAncestry(Node<V> a) {
        Stack<Node<V>> stack = new Stack<>();
        // 将长集合放入栈中
        while (a != parents.get(a)) {
            stack.push(a);
            a = parents.get(a);
        }

        // 将长集合的每个集合指向祖先节点，优化树的高度
        while (!stack.isEmpty()) {
            parents.put(stack.pop(), a);
        }
        return a;
    }

    private static class Node<V>{
        V value;
        public Node(V v) {
            this.value = v;
        }

    }
}
