package com.gqs.algorithm.class012_graph;

import java.util.HashMap;

public class T009_Dijkstra {

    /**
     * 获取图的最小距离表
     * 1、将元素都放入小根堆
     * 2、依次弹出数据的值就是距离表的数据，逐个处理每个节点的边
     *  - 存在就更新最小的距离
     *  - 不存在就插入元素
     */
    public static HashMap<Graph.Node, Integer> getDijkstra(Graph.Node node, int size) {
        // 创建加强堆，并将节点放入到堆中
        NodeHeap nodeHeap = new NodeHeap(size);
        HashMap<Graph.Node, Integer> result = new HashMap<>();
        nodeHeap.addOrUpdate(node, 0);

        // 堆不为空则处理数据
        if(!nodeHeap.isEmpty()) {
            RecordNode record = nodeHeap.pop();
            Graph.Node rNode = record.node;
            Integer rDistance = record.distance;
            // 处理节点的所有边
            for (Graph.Edge edge : rNode.edges) {
                // 添加或者更新堆的最小距离
                nodeHeap.addOrUpdate(edge.to, rDistance + edge.weight);
            }
            result.put(rNode, rDistance);
        }
        return result;
    }

    // 小根堆
    public static class NodeHeap{
        // 数组存放堆
        private Graph.Node[] nodes;
        // 堆下标映射, 反向索引表
        private HashMap<Graph.Node, Integer> heapIndexMap;
        // 距离映射
        private HashMap<Graph.Node, Integer> distanceMap;
        private int size;

        public NodeHeap(int size) {
            this.nodes = new Graph.Node[size];
            this.heapIndexMap = new HashMap<>();
            this.distanceMap = new HashMap<>();
            this.size = 0;
        }

        public void addOrUpdate(Graph.Node node, int distance) {
            // 在堆里面，进行更新
            if(inHeap(node)) {
                distanceMap.put(node, Math.min(distance, distanceMap.get(node)));
                insertHeapify(heapIndexMap.get(node));
            }

            if(!isEntered(node)) {
                // 将值放到最后面
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(size++);
            }
        }

        // 插入调整堆(上浮)
        private void insertHeapify(Integer index) {
            // 比较当前index的距离和父距离
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) /2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }


        public boolean isEmpty() {
            return size == 0;
        }
        // 判断是否放入过堆中
        public boolean isEntered(Graph.Node node) {
            return heapIndexMap.containsKey(node);
        }
        // 判断是否在堆里面
        public boolean inHeap(Graph.Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        public RecordNode pop() {
            RecordNode recordNode = new RecordNode(nodes[0], distanceMap.get(nodes[0]));
            // 将最后面的值放在最前面
            swap(0, size - 1);
            // 将弹出的值下标置为-1
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapify(0, --size);
            return recordNode;
        }

        // 下沉
        private void heapify(int i, int size) {
            int left = i * 2 + 1;
            while (left < size) {
                // 获取左右孩子中最小值的下标
                int minVal = (left + 1) < size && distanceMap.get(nodes[left + 1])
                        < distanceMap.get(nodes[left]) ? left + 1 : left;
                // 最小值和当前值获取最小的
                minVal = distanceMap.get(nodes[minVal]) < distanceMap.get(nodes[i])
                        ? minVal : i;
                // i的距离就是最大的
                if(minVal == i) {
                    break;
                }
                swap(minVal, i);
                i = minVal;
                // 计算左边的下标值
                left = i * 2 +1;
            }
        }

        private void swap(int i, int j) {
            heapIndexMap.put(nodes[i], j);
            heapIndexMap.put(nodes[j], i);
            Graph.Node tmp = nodes[i];
            nodes[i] = nodes[j];
            nodes[j] = tmp;
        }
    }

    private static class RecordNode{

        public Graph.Node node;
        public Integer distance;

        public RecordNode(Graph.Node node, Integer distance) {
            this.node = node;
            this.distance = distance;
        }
    }

}
