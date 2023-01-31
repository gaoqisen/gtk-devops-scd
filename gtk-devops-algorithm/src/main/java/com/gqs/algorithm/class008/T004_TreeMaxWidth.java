package com.gqs.algorithm.class008;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class T004_TreeMaxWidth {


    public static void main(String[] args) {
        Node head = new Node("A");
        head.left = new Node("B");
        head.right = new Node("C");
        head.left.left = new Node("D");
        head.left.right = new Node("E");
        head.right.left = new Node("F");

        System.out.println(maxWidthUseMap(head));
    }


    public static int maxWidthUseMap(Node head) {
        if(head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        // 记录所有节点的当前层数
        Map<Node, Integer> map = new HashMap<>();
        map.put(head, 1);
        int currentLevel = 1;
        int levelNumber = 1;
        int max = 0;
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            // 获取当前节点的层数
            Integer level = map.get(poll);
            if(poll.left != null) {
                queue.add(poll.left);
                map.put(poll.left, level + 1);
            }
            if(poll.right != null) {
                queue.add(poll.right);
                map.put(poll.right, level +1);
            }
            // 如果是同一个层级则增加当前层数数量
            if(level == currentLevel) {
                levelNumber++;
            }
            // 非同一个层级则遍历到了下一个层级，层级+1和获取最大层级数量
            else {
                levelNumber = 1;
                currentLevel++;
                max = Math.max(max, levelNumber);
            }
        }
        return Math.max(levelNumber, max);
    }

    public static class Node {
        public String value;
        public Node left;
        public Node right;

        public Node(String data) {
            this.value = data;
        }
    }

}
