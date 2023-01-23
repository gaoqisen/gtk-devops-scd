package com.gqs.algorithm.class006;

import com.alibaba.fastjson.JSONObject;

import java.util.Stack;

/**
 * 判断是否是回文
 */
public class T002_IsPalindromes {

    public static void main(String[] args) {
        Node node = Node.build(1);
        node.setNext(2).setNext(2).setNext(3).setNext(2).setNext(2).setNext(1);
        System.out.println(JSONObject.toJSONString(node));
        System.out.println(isPalindromes(node));
        System.out.println(JSONObject.toJSONString(node));
        System.out.println(isPalindromes1(node));

        System.out.println(isPalindromes2(node));



    }

    // 用栈实现回文，先将节点放入栈中。之后弹出值与节点数据进行比较
    public static boolean isPalindromes(Node node) {
        Node current = node;
        Stack<Integer> stack = new Stack<>();
        while (current != null) {
            stack.push(current.val);
            current = current.next;
        }

        Node i = node;
        while (i != null) {
            if(i.val != stack.pop()) {
                return false;
            }
            i = i.next;

        }
        return true;
    }

    // 利用快慢指针实现，空间复杂度小一半
    public static boolean isPalindromes1(Node node) {
        // 利用快慢指针获取节点的中间数，奇数获取中间值，偶数获取中下节点
        Node fast = node.next;
        Node slow = node.next;
        while (fast.next !=null && fast.next.next !=null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 将中间值后面的元素放入栈中
        Stack<Integer> stack = new Stack<>();
        while (slow != null) {
            stack.push(slow.val);
            slow = slow.next;
        }

        // 对比栈里面抛出的元素和节点元素，不一样则不是回文
        Node current = node;
        while (!stack.isEmpty()) {
            if(current.val != stack.pop()) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    // 不用堆，直接找到中值后进行数字比较
    public static boolean isPalindromes2(Node node) {
        Node fast = node;
        Node slow = node;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 逆序后面的数据
        Node newHead = slow.next; // fast是中间数后面的值
        slow.next = null; // 从中间切断
        Node temp = null;
        while (newHead!= null) {
            temp = newHead.next;
            newHead.next = slow;
            slow = newHead;
            newHead = temp;
        }

        temp  = node;
        fast = slow;
        boolean res = true;
        while (temp != null && fast!=null) {
            if(temp.val != fast.val) {
                res = false;
                break;
            }
            temp = temp.next;
            fast = fast.next;
        }

        // 重新逆序还原
        temp = slow.next;
        slow.next = null;
        while (temp != null) {
            fast = temp.next;
            temp.next = slow;
            slow = temp;
            temp = fast;
        }

        return res;
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
