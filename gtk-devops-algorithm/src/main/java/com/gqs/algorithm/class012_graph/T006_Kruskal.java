package com.gqs.algorithm.class012_graph;

import java.util.*;

public class T006_Kruskal {

    public static Set<Graph.Edge> kruskalMST(Graph graph) {
        // 创建并查集并初始化
        UnionSet unionSet = new UnionSet();
        unionSet.makeSets(graph.nodes.values());

        // 将所有的边放入优先级队列
        PriorityQueue<Graph.Edge> queue = new PriorityQueue<>((a, b) -> {
            return a.weight - b.weight;
        });
        for (Graph.Edge edge : graph.edges) {
            queue.add(edge);
        }

        Set<Graph.Edge> set = new HashSet<>();
        while (!queue.isEmpty()) {
            // 获取最小的边
            Graph.Edge poll = queue.poll();
            // 如果边的两端不再一个集合则将当前边就是结果值
            if(!unionSet.isSame(poll.from, poll.to)) {
                set.add(poll);
                // 合并当前边
                unionSet.union(poll.from, poll.to);
            }
        }
        return set;
    }


    public static class UnionSet{

        private HashMap<Graph.Node, Graph.Node> father;

        private HashMap<Graph.Node, Integer> sizes;

        public UnionSet() {
            father = new HashMap<>();
            sizes = new HashMap<>();
        }

        private Graph.Node findFather(Graph.Node node) {
            Stack<Graph.Node> stack = new Stack<>();
            while (node != father.get(node)) {
                stack.add(node);
                node = father.get(node);
            }

            while (!stack.isEmpty()) {
                father.put(stack.pop(), node);
            }
            return node;
        }

        // 初始化节点
        public void makeSets(Collection<Graph.Node> nodes) {
            father.clear();
            sizes.clear();
            for (Graph.Node node : nodes) {
                father.put(node, node);
                sizes.put(node, 1);
            }
        }

        public boolean isSame(Graph.Node a, Graph.Node b) {
            Graph.Node aNode = findFather(a);
            Graph.Node bNode = findFather(b);
            return aNode == bNode;
        }

        public void union(Graph.Node a, Graph.Node b) {
            Graph.Node aNode = findFather(a);
            Graph.Node bNode = findFather(b);
            if(aNode != bNode) {
                int aLength = sizes.get(aNode);
                int bLength = sizes.get(bNode);
                if(aLength > bLength) {
                    father.put(bNode, aNode);
                    sizes.put(aNode, aLength + bLength);
                    sizes.remove(bNode);
                } else {
                    father.put(aNode, bNode);
                    sizes.put(bNode, aLength + bLength);
                    sizes.remove(aNode);
                }
            }
        }
    }





}
