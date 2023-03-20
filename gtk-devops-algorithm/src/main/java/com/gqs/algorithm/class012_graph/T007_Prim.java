package com.gqs.algorithm.class012_graph;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class T007_Prim {

    public static Set<Graph.Edge> primMST(Graph graph) {
        // 创建优先级队列，小根堆。每次获取最小边
        PriorityQueue<Graph.Edge> queue = new PriorityQueue<>((a, b) -> {
            return a.weight - b.weight;
        });
        // 存储解锁的节点，防止往回走
        Set<Graph.Node> nodes = new HashSet<>();
        // 存储选中的边
        Set<Graph.Edge> result = new HashSet<>();

        for (Graph.Node node : graph.nodes.values()) {
            nodes.add(node);
            // 点解锁所有的边
            for (Graph.Edge edge : node.edges) {
                queue.add(edge);
            }
            while (!queue.isEmpty()) {
                // 优先处理解锁边里面最小的边
                Graph.Edge poll = queue.poll();
                // 解锁往后指向的节点，并判断节点是否是新的节点
                Graph.Node to = poll.to;
                if(!nodes.contains(to)) {
                    // 不是新节点则将节点放入解锁列表，以及放入选中表
                    nodes.add(to);
                    result.add(poll);
                    // 将其他边放入优先级队列
                    for (Graph.Edge edge : to.edges) {
                        queue.add(edge);
                    }
                }
            }
            // 防止森林
            // break;
        }
        return result;
    }

}
