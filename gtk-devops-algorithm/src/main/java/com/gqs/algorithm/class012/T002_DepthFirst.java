package com.gqs.algorithm.class012;

import java.util.HashSet;
import java.util.Stack;

public class T002_DepthFirst{

    // 深度优先遍历
    public static void depthFirst(Graph.Node node) {
        // 栈就是深度遍历的整条路径
        Stack<Graph.Node> stack = new Stack<>();
        HashSet<Graph.Node> set = new HashSet<>();
        stack.push(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.isEmpty()) {
            Graph.Node pop = stack.pop();
            for (Graph.Node next : pop.nexts) {
                if(!set.contains(next)) {
                    // 将弹出的节点重新压入
                    stack.push(pop);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    // 打印后弹出
                    break;
                }
            }
        }
    }
    
}