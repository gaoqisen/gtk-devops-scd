package com.gqs.algorithm.other;

/**
 * 约瑟夫问题：
 *
 * 约瑟夫和他的朋友以及39个犹太人宁死不愿被抓，按照环形坐下后，从指定位置开始顺序数固定数字，数到固定数字的人自杀。
 * 而约瑟夫和他朋友站在16和32个位置上则留到了最后
 *
 */
public class D06Josepfu {

    public static void main(String[] args) {
        CircleSingLinkedList circleSingLinkedList = new CircleSingLinkedList();
        circleSingLinkedList.addBoy(5);
        circleSingLinkedList.showBoy();
        circleSingLinkedList.countBoy(1, 2, 5);
    }

}

// 环形的单向链表
class CircleSingLinkedList{

    private Boy first = new Boy(-1);

    public void addBoy(int nums) {
        if(nums < 1) {
            System.out.println("数量值不对");
            return;
        }

        Boy curBoy = null;
        for (int i = 1; i <= nums; i++) {
            Boy boy = new Boy(i);
            if(i == 1) {
                first = boy;
                first.setNext(first);
                curBoy = first;
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    public void showBoy() {
        if(first == null) {
            System.out.println("first链表为空");
            return;
        }
        Boy curBoy = first;
        while (true) {
            System.out.printf("no: %d \n", curBoy.getNo());
            if(curBoy.getNext() == first) {
                break;
            }
            curBoy = curBoy.getNext();
        }
    }

    /**
     *
     * @param startNo 从第几个开始
     * @param countNum 数几下
     * @param nums 最初数量
     */
    public void countBoy(int startNo, int countNum, int nums) {
        if(first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数有误，请重新输入");
            return;
        }
        Boy helper = first;

        // 找到开始的位置
        while (true) {
            if(helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        // 遍历出圈
        while (true) {
            if(helper == first) {
                break;
            }

            for (int i = 0; i < countNum; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            System.out.println("出圈"+ first.getNo());
            first = first.getNext();
            helper.setNext(first);
        }

        System.out.println("保留的数据"+ helper.getNo());

    }

}

class Boy{

    private int no;
    private Boy next;
    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
