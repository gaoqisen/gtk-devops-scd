package com.gqs.algorithm.class012;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
https://www.lintcode.com/problem/127/description
 */
public class T004_ToplogicalOrderBFS {
    class DirectedGraphNode {
        int label;
        List<DirectedGraphNode> neighbors;
        DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    private static class Record{

        private DirectedGraphNode node;
        private long nodes;

        public Record(DirectedGraphNode node, long nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }

    // 图排序
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // 通过动态规划将每个节点以及节点的数量放到map里面
        HashMap<DirectedGraphNode, Record> hashMap = new HashMap<>();
        for (DirectedGraphNode directedGraphNode : graph) {
            getRecord(directedGraphNode, hashMap);
        }

        // 通过数量进行排序
        List<Record> records = new ArrayList<>();
        for (Record value : hashMap.values()) {
            records.add(value);
        }
        records.sort((a, b) -> {
            return a.nodes == b.nodes ? 0 : (a.nodes > b.nodes ? -1 : 1);
        });

        // 排序后的节点就是图的排序
        ArrayList<DirectedGraphNode> result = new ArrayList<>();
        for (Record record : records) {
            result.add(record.node);
        }
        return result;
    }

    // 通过递归获取点数
    private static Record getRecord(DirectedGraphNode node, Map<DirectedGraphNode, Record> map) {
        // 通过动态规划如果已经存在则直接获取
        if(map.containsKey(node)) {
            return map.get(node);
        }

        // 计算点数
        long nums = 0;
        for (DirectedGraphNode neighbor : node.neighbors) {
            nums += getRecord(neighbor, map).nodes;
        }

        // 封装数据后返回
        Record record = new Record(node, nums + 1);
        map.put(node, record);
        return record;
    }
}
