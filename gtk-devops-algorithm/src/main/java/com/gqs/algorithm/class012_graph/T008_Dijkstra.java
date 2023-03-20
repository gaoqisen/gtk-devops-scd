package com.gqs.algorithm.class012_graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class T008_Dijkstra {

    /**
     * 获取图的最小距离表
     */
    public static HashMap<Graph.Node, Integer> getDijkstra(Graph.Node node) {
        // 创建距离表
        HashMap<Graph.Node, Integer> map = new HashMap<>();
        // 创建选中表
        Set<Graph.Node> selected = new HashSet<>();
        map.put(node, 1);

        // 获取最小节点，第一次就是当前节点
        Graph.Node minNode = getMinNode(map, selected);
        if(minNode != null) {
            // 获取当前节点的最小距离
            Integer distance = map.get(minNode);
            // 遍历所有的边
            for (Graph.Edge edge : minNode.edges) {
                // 如果当前边在距离表中不存在，则存入距离表，距离=当前节点最小距离+当前边距离
                Graph.Node to = edge.to;
                Integer currentDistance = distance + edge.weight;
                if(!map.containsKey(edge)) {
                    map.put(to, currentDistance);
                }
                // 在距离表中存在，则更新最小距离
                else {
                    map.put(to, Math.min(map.get(to), currentDistance));
                }
            }
            // 将最小距离节点标记为选中
            selected.add(minNode);
            // 获取下一个选中距离
            minNode = getMinNode(map, selected);
        }
        return map;
    }

    /**
     * 在距离表中获取没有选中过的最小距离的节点
     */
    private static Graph.Node getMinNode(HashMap<Graph.Node, Integer> map, Set<Graph.Node> selected) {
        Integer min = Integer.MAX_VALUE;
        Graph.Node minNode = null;
        for (Map.Entry<Graph.Node, Integer> entry : map.entrySet()) {
            Integer distance = entry.getValue();
            Graph.Node node = entry.getKey();
            if(!selected.contains(node) && distance < min) {
                minNode = node;
                min = distance;
            }
        }
        return minNode;
    }


}
