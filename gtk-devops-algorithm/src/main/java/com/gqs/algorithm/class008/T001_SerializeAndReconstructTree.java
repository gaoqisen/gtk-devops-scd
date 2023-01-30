package com.gqs.algorithm.class008;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class T001_SerializeAndReconstructTree {

    public static void level(Node node) {
        if(node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            // 弹出队列里面的值打印
            Node poll = queue.poll();
            System.out.print(poll.val);

            // 队列里面的存在left｜right则放入队列中
            if(poll.left != null) {
                queue.add(poll.left);
            }

            if(poll.right != null) {
                queue.add(poll.right);
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node("A");
        head.left = new Node("B");
        head.right = new Node("C");
        head.left.left = new Node("D");
        head.left.right = new Node("E");
        head.right.left = new Node("F");
        level(head);
        System.out.println("----");
        // 先序序列化
        Queue<String> strings = preSerialize(head);
        Node node = preUnSerialize(strings);
        level(node);
        System.out.println("----");

        // 后序序列化
        Queue<String> stringQueue = posSerialize(head);
        Node unSerialize = posUnSerialize(stringQueue);
        level(unSerialize);
        System.out.println("----");

        // 按层序列化
        Queue<String> levelSerialize = levelSerialize(node);
        Node unSerialize1 = levelUnSerialize(levelSerialize);
        level(unSerialize);

    }


    // 先序序列化
    public static Queue<String> preSerialize(Node node) {
        if(node == null) {
            return null;
        }
        Queue<String> queue = new LinkedList<>();
        pre(node, queue);
        return queue;
    }

    private static void pre(Node node, Queue<String> queue) {
        if(node == null) {
            queue.add(null);
            return;
        }
        queue.add(node.val);
        pre(node.left, queue);
        pre(node.right, queue);
    }

    // 先序反序列化
    public static Node preUnSerialize(Queue<String> queue) {
        if(queue == null || queue.isEmpty()) {
            return null;
        }
        return pre(queue);
    }

    private static Node pre(Queue<String> queue) {
        String poll = queue.poll();
        if(poll == null) {
            return null;
        }
        Node node = new Node(poll);
        node.left = pre(queue);
        node.right = pre(queue);
        return node;
    }

    // 后序序列化
    public static Queue<String> posSerialize(Node node) {
        if(node == null) {
            return null;
        }
        Queue<String> queue = new LinkedList<>();
        pos(node, queue);
        return queue;
    }

    private static void pos(Node node, Queue<String> queue) {
        if(node == null) {
            queue.add(null);
            return;
        }

        pos(node.left, queue);
        pos(node.right, queue);
        queue.add(node.val);
    }

    // 后序反序列化
    public static Node posUnSerialize(Queue<String> queue) {
        if(queue == null || queue.isEmpty()) {
            return null;
        }

        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        return pos(stack);
    }

    public static Node pos(Stack<String> stack) {
        String pop = stack.pop();
        if(pop == null) {
            return null;
        }
        Node node = new Node(pop);
        node.right = pos(stack);
        node.left = pos(stack);
        return node;
    }

    // 按层序列化
    public static Queue<String> levelSerialize(Node node) {
        Queue<String> res = new LinkedList<>();
        if(node == null) {
            res.add(null);
            return res;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        res.add(node.val);
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            if(poll.left != null) {
                queue.add(poll.left);
                res.add(poll.left.val);
            } else {
                res.add(null);
            }

            if(poll.right != null) {
                queue.add(poll.right);
                res.add(poll.right.val);
            } else {
                res.add(null);
            }
        }
        return res;
    }

    // 按层反序列化
    public static Node levelUnSerialize(Queue<String> queue){
        if(queue == null || queue.size() < 1) {
            return null;
        }
        Node head = generateNode(queue.poll());
        Queue<Node> nodeQueue = new LinkedList<>();
        if(head != null) {
            nodeQueue.add(head);
        }

        Node node = null;
        while (!nodeQueue.isEmpty()) {
            node = nodeQueue.poll();
            node.left = generateNode(queue.poll());
            node.right = generateNode(queue.poll());
            if(node.left != null) {
                nodeQueue.add(node.left);
            }
            if(node.right != null) {
                nodeQueue.add(node.right);
            }
        }
        return node;
    }

    private static Node generateNode(String val) {
        if(val == null) {
            return null;
        }
        return new Node(val);
    }

    public static class Node{

        public String val;

        public Node left;

        public Node right;

        public Node(String val) {
            this.val = val;
        }

    }
}
