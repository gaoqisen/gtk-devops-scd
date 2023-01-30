package com.gqs.algorithm.class007;

import com.alibaba.fastjson.JSONObject;

public class T003_DelTraversal {

    public static void main(String[] args) {
        Node node = Node.build(1);
        node.setNext(2).setNext(2).setNext(3).setNext(1).setNext(6);
        System.out.println(JSONObject.toJSONString(node));

        Node tail = del(node, node.next.next.next.next.next);
        System.out.println(JSONObject.toJSONString(tail));

        Node level = del(node, node.next.next);
        System.out.println(JSONObject.toJSONString(level));

        Node level1 = del(node, node);
        System.out.println(JSONObject.toJSONString(level1));


    }

    /**
     * 1、删除节点返回必须是节点，可能删除的就是头节点
     * 2、只是给删除的节点，不给头节点。只能往后遍历，获取不到前面的节点。 无法删除最后一个节点
     */
    public static Node del(Node node, Node level) {
        if(node == null || level == null) {
            return node;
        }
        Node current = node;
        // 只删除单链表无环
        while (true) {
            if(current == level) {
                current.val = current.next.val;
                current.next =  current.next.next;
                break;
            } else if(current.next.next == null) {
                current.next = null;
                break;
            }
            current = current.next;
        }
        return node;
    }


    public static class Node{

        public int val;

        public Node next;

        public Node setNext(int val) {
            Node build = build(val);
            this.next = build;
            return build;
        }

        public static Node build(int val) {
            return new Node(val);
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(){}
    }
}
