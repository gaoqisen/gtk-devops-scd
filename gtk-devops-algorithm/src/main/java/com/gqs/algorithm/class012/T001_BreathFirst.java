package com.gqs.algorithm.class012;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

// 宽度优先遍历
public class T001_BreathFirst {

    // 遍历的时候必须要用set标记是否已经遍历过，节点可能往回指
    public static void breathFirst(Graph.Node node) {
        Queue<Graph.Node> queue = new LinkedList<>();
        HashSet<Graph.Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()) {
            Graph.Node poll = queue.poll();
            System.out.println(poll.value);
            for (Graph.Node next : poll.nexts) {
                if(!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

}
