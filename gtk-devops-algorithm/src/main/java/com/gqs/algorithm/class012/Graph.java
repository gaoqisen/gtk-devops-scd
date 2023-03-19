package com.gqs.algorithm.class012;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// 图
public class Graph {

    // 存储所有的节点映射关系
    public HashMap<Integer, Node> nodes;
    // 记录所有的边
    public HashSet<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }

    // 通过矩阵生成图
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int[] ints : matrix) {
            int weight = ints[0];
            int from = ints[1];
            int to = ints[2];

            // 将节点加入到节点列表
            if(!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if(!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }

            // 获取节点
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);

            // 边数据封装
            Edge edge = new Edge();
            edge.from = fromNode;
            edge.to = toNode;
            edge.weight = weight;

            // from数据封装
            fromNode.out++;
            fromNode.nexts.add(toNode);
            fromNode.edges.add(edge);

            toNode.in++;
            // 边是整个边的一部分
            graph.edges.add(edge);
        }
        return graph;
    }

    public static class Node{
        // 当前节点的值
        public int value;
        // 连接到当前节点的数量
        public int in;
        // 连接出去的节点数量
        public int out;
        // 连接出去的节点列表
        public ArrayList<Node> nexts;
        // 连接出去的边
        public ArrayList<Edge> edges;


        public Node(int value) {
            this.value = value;
            this.in = 0;
            this.out = 0;
            this.nexts = new ArrayList<>();
            this.edges = new ArrayList<>();
        }

    }

    // 边
    public static class Edge{
        // 权重
        public int weight;
        public Node from;
        public Node to;
    }


}
