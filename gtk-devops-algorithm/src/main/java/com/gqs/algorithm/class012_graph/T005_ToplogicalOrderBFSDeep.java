package com.gqs.algorithm.class012_graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
https://www.lintcode.com/problem/127/description
 */
public class T005_ToplogicalOrderBFSDeep {
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
        private int deep;

        public Record(DirectedGraphNode node, int deep) {
            this.node = node;
            this.deep = deep;
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
        records.sort((a, b) -> (b.deep - a.deep));

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
        int deep = 0;
        for (DirectedGraphNode neighbor : node.neighbors) {
            deep = Math.max(deep, getRecord(neighbor, map).deep);
        }

        // 封装数据后返回
        Record record = new Record(node, deep + 1);
        map.put(node, record);
        return record;
    }
}
