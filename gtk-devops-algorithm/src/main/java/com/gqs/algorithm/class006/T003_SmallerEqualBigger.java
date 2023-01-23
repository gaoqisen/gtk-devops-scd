package com.gqs.algorithm.class006;


import com.alibaba.fastjson.JSONObject;

/**
 * 将给定链表进行小于等于大于分区
 */
public class T003_SmallerEqualBigger {
    
    
    public static void main(String[] args) {
        Node node = Node.build(1);
        node.setNext(2).setNext(2).setNext(3).setNext(2).setNext(2).setNext(1).setNext(6);
        System.out.println(JSONObject.toJSONString(node));

        Node p = partition(node, 2);

        System.out.println(JSONObject.toJSONString(node));
        System.out.println(JSONObject.toJSONString(p));

    }

    /**
     * 通过数组分区， 额外空间复杂度高
     */
    public static void partitionArray(Node node, int pivot) {
        // 1、创建一个数组和节点一样长
        // 2、通过数组进行节点分区排序，荷兰国旗
        // 3、将数组里面的节点依次串连，最后一个节点置为null后返回数组第一个节点

    }

    /**
     * 通过有限几个变量进行分区，额外空间复杂度低
     */
    public static Node partition(Node node, int pivot) {
        // 1、定义小于区域的头和尾节点，等于区域的头和尾节点，大于区域的头和尾节点
        Node smallHead = null, smallTail = null;
        Node equalHead = null, equalTail = null;
        Node bigHead = null, bigTail = null;

        // 2、遍历所有节点，将小于、等于、大于的区域通过头尾节点串连
        Node temp = null;
        while (node != null) {
            temp = node.next;
            node.next = null;
            if(node.val < pivot) {
                if(smallHead == null) {
                    smallHead = node;
                    smallTail = node;
                } else {
                    smallTail.next = node;
                    smallTail = node;
                }
            }

            if(node.val == pivot) {
                if(equalHead == null) {
                    equalHead = node;
                    equalTail = node;
                } else {
                    equalTail.next = node;
                    equalTail = node;
                }
            }

            if(node.val > pivot) {
                if(bigHead == null) {
                    bigHead = node;
                    bigTail = node;
                } else {
                    bigTail.next = node;
                    bigTail = node;
                }
            }
            node = temp;
        }

        // 3、将小于区域的尾节点连等于区域的头，等于区域的尾连大于区域的头
        if(smallHead != null) {
            // 小于区域存在值，则将小于区域的next指向等于区域的头
            smallTail.next = equalHead;
            // 等于区域有值则等于区域保持不变，否则等于区域的尾为小于区域的尾
            equalTail = equalTail == null ? smallTail : equalTail;
        }

        if(equalHead != null) {
            equalTail.next = bigHead;
        }

        if(smallHead != null) {
            return smallHead;
        }
        if(equalHead != null) {
            return equalHead;
        }

        return bigHead;
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
