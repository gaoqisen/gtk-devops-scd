package com.gqs.algorithm.other;

import java.math.BigInteger;

public class Day04AddNumber {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(9);
        ListNode listNode1 = new ListNode(3, listNode);
        ListNode listNode2 = new ListNode(5, listNode1);

        ListNode listNode5 = new ListNode(8);
        ListNode listNode6 = new ListNode(2, listNode5);
        ListNode listNode7 = new ListNode(8, listNode6);

        System.out.println(addTwoNumbers(listNode2, listNode7));
    }

    /**
     * 给定连个列表【2，3，4】【6，2，5】
     * 然后逆序相加后逆序输出
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        String val1 = lx(getString(l1));
        String val2 = lx(getString(l2));
        BigInteger num = new BigInteger(val1).add(new BigInteger(val2));
        char[] chars = String.valueOf(num).toCharArray();
        ListNode listNode = null;
        for (int i = 0; i < chars.length ; i++) {
            int val = Integer.valueOf(String.valueOf(chars[i]));
            if(listNode != null) {
                listNode = new ListNode(val, listNode);
            } else {
                listNode = new ListNode(val);
            }
        }
        return listNode;
    }

    private static String lx(String a) {
        String str = "";
        for (int i = a.length()-1; i >= 0; i--) {
            str += a.charAt(i);
        }
        return str;
    }

    private static String getString(ListNode listNode) {
        String val = listNode.val + "";
        if(listNode.next != null) {
            val += getString(listNode.next);
        }
        return val;
    }

}
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}