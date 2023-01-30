package com.gqs.algorithm.class006;

/**
 * 查找第一个相交节点
 *
 * https://leetcode.cn/problems/3u1WK4/submissions/
 */
public class T005_FindFirstIntersect {


    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);

        // 0->9->8->6->7->null
        ListNode head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectionNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectionNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectionNode(head1, head2).value);

    }

    public static ListNode getIntersectionNode(ListNode listNode1, ListNode listNode2) {
        if(listNode1 == null || listNode2 == null) {
            return null;
        }

        ListNode loop1 = findFirstInputLoopNode(listNode1);
        ListNode loop2 = findFirstInputLoopNode(listNode2);

        // 两个无环链表相交
        if(loop1 == null && loop2 == null) {
            return notLoopNodeIntersect(listNode1, listNode2);
        }

        // 两个链表都有环
        if(loop1 != null && loop2 != null) {
            return loopNodeIntersect(listNode1, loop1, listNode2, loop2);
        }

        // 两个链表其中一个有环，不可能相交
        return null;
    }

    /**
     * 两个有环单链表
     * - loop1和loop2的地址相等，就是两个无环链表求第一个相交节点
     * - loop1和loop2的地址不相等，遍历链表一如果在链表中没遇到loop2则无相交节点，遇到loop2则存在相交节点返回loop1或loop2都可以。
     */
    public static ListNode loopNodeIntersect(ListNode listNodeOne, ListNode loopOne, ListNode listNodeTwo, ListNode loopTwo) {
        ListNode currentOne = null;
        ListNode currentTwo = null;
        if(loopOne == loopTwo){
            currentOne = listNodeOne;
            currentTwo = listNodeTwo;
            int i = 0;
            while (currentOne != loopOne) {
                currentOne = currentOne.next;
                i++;
            }

            while (currentTwo != loopTwo) {
                currentTwo = currentTwo.next;
                i--;
            }

            // 将长的赋值给lastNodeOne, 短的赋值给lastNodeTwo
            currentOne = i > 0 ? listNodeOne : listNodeTwo;
            currentTwo = currentOne == listNodeOne ? listNodeTwo : listNodeOne;

            i = Math.abs(i);
            while (i != 0) {
                currentOne = currentOne.next;
                i--;
            }
            while (currentOne != currentTwo) {
                currentOne = currentOne.next;
                currentTwo = currentTwo.next;
            }
            return currentOne;
        }

        // loop1和loop2的地址不相等，遍历链表一如果在链表中没遇到loop2则无相交节点，遇到loop2则存在相交节点返回loop1或loop2都可以。
        else {
            currentOne = listNodeOne.next;
            while (currentOne != loopOne) {
                if(currentOne == loopTwo) {
                    return loopTwo;
                }
                currentOne = currentOne.next;
            }
            return null;
        }
    }


    /**
     * 两个无环单链表
     *
     * 遍历两个链表如果最后一个节点的地址相同则相交。
     * 长的链表先走 两个节点的长度差 后在判断两个节点地址是否相同，第一个相等的节点就是相交节点。
     */
    public static ListNode notLoopNodeIntersect(ListNode listNodeOne, ListNode listNodeTwo) {
        ListNode lastListNodeOne = listNodeOne;
        int nodeLength = 0;
        while (lastListNodeOne.next != null) {
            lastListNodeOne = lastListNodeOne.next;
            nodeLength++;
        }

        ListNode lastListNodeTwo = listNodeTwo;
        while (lastListNodeTwo.next != null) {
            lastListNodeTwo = lastListNodeTwo.next;
            nodeLength--;
        }
        // 最后一个节点的地址不相同则不会相交
        if(lastListNodeOne != lastListNodeTwo) {
            return null;
        }

        // 将长的赋值给lastNodeOne, 短的赋值给lastNodeTwo
        lastListNodeOne = nodeLength > 0 ? listNodeOne : listNodeTwo;
        lastListNodeTwo = lastListNodeOne == listNodeOne ? listNodeTwo : listNodeOne;

        // 长的节点先走掉差值
        int abs = Math.abs(nodeLength);
        while (abs != 0) {
            lastListNodeOne = lastListNodeOne.next;
            abs--;
        }

        // 走到相等为止
        while (lastListNodeOne != lastListNodeTwo) {
            lastListNodeOne = lastListNodeOne.next;
            lastListNodeTwo = lastListNodeTwo.next;
        }
        return lastListNodeOne;
    }

    /**
     * 获取第一个入环节点，没有入环节点则返回null
     *
     * 实现方式：利用快慢指针(快2慢1)，走到相交节点时快指针回到头，之后快指针步长改为1，
     * 重新相遇后的第一个节点就是入环的第一个的节点，快指针遇到null则链表节点无入环节点
     */
    public static ListNode findFirstInputLoopNode(ListNode head) {
        if(head == null || head.next == null || head.next.next == null) {
            return null;
        }
        ListNode fast = head.next.next, slow = head.next;
        boolean isIntersect = false, firstHead = true;
        while (fast != null) {
            if(fast == slow) {
                // 第二次相遇的节点直接返回
                if(isIntersect) {
                    return slow;
                }
                isIntersect = true;
            }
            slow = slow.next;
            if(fast.next == null) {
                return null;
            }
            if(isIntersect) {
                if(firstHead) {
                    firstHead = false;
                    fast = head;
                }
                fast = fast.next;
            } else {
                fast = fast.next.next;
            }
        }
        return null;
    }




    public static class ListNode {

        public int value;

        public ListNode next;


        public void setNext(ListNode listNode) {
            this.next = listNode;
        }

        public ListNode setNext(int val) {
            ListNode build = build(val);
            this.next = build;
            return build;
        }

        public static ListNode build(int val) {
            return new ListNode(val);
        }

        public ListNode(int val) {
            this.value = val;
        }

        public ListNode(){}
    }

}
