package com.gqs.algorithm.class012_graph;

import java.util.*;

public class T003_TopologicalSort {

    public static List<Graph.Node> sortedTopology(Graph graph) {
        HashMap<Graph.Node, Integer> hashMap = new HashMap<>();
        Queue<Graph.Node> queue = new LinkedList<>();

        // 将所有的入度放入hashmap，如果入度为0就放入到队列中
        graph.nodes.forEach((k,v) -> {
            hashMap.put(v, v.in);
            if(v.in == 0) {
                queue.add(v);
            }
        });

        List<Graph.Node> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Graph.Node poll = queue.poll();
            result.add(poll);

            // 遍历后面的节点列表，如果先将节点减一后。判断节点数为0就放入队列
            for (Graph.Node next : poll.nexts) {
                hashMap.put(next, hashMap.get(next) - 1);
                if(hashMap.get(next) == 0) {
                    // 放入队列的节点就是排序后的节点
                    queue.add(next);
                }
            }
        }
        return result;
    }



}
