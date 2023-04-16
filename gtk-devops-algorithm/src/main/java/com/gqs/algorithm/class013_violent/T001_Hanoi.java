package com.gqs.algorithm.class013_violent;

public class T001_Hanoi {


    public static void leftToRight(int n) {
        if(n == 1) {
            System.out.println("从左往右移动1");
            return;
        }
        leftToMid(n - 1);
        System.out.println("从左往右移动" + n);
        midToRight(n - 1);
    }

    public static void leftToMid(int n) {
        if(n == 1) {
            System.out.println("从左往中移动1");
            return;
        }
        leftToRight(n - 1);
        System.out.println("从左往中移动" + n);
        rightToMid(n - 1);
    }

    public static void midToRight(int n) {
        if(n == 1) {
            System.out.println("从中往右移动1");
            return;
        }
        midToLeft(n - 1);
        System.out.println("从中往左移动" + n);
        leftToRight(n - 1);
    }

    public static void midToLeft(int n) {
        if(n == 1) {
            System.out.println("从中往左移动1");
            return;
        }
        midToRight(n - 1);
        System.out.println("从中往左移动" + n);
        rightToLeft(n - 1);
    }

    public static void rightToMid(int n) {
        if(n == 1) {
            System.out.println("从右往中移动1");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("从右往中移动" + n);
        leftToMid(n - 1);
    }

    public static void rightToLeft(int n) {
        if(n == 1) {
            System.out.println("从右往左移动1");
            return;
        }
        rightToMid(n - 1);
        System.out.println("从右往左移动" + n);
        midToLeft(n - 1);
    }

    public static void hanoi(int n) {
        leftToRight(n);
    }


    public static void main(String[] args) {
        hanoi(3);
        System.out.println("------------");
        hanoiPro(3);
    }

    public static void hanoiPro(int n) {
        common(n,"左", "右", "中");
    }

    // 优化的通用版本，将左右中变为参数
    public static void common(int n, String left, String right, String mid) {
        if(n == 1) {
            System.out.println("从" + left + "移动到" + right + "1");
            return;
        }
        common(n - 1, left, mid, right);
        System.out.println("从" + left + "移动到" + right + n);
        common(n - 1, mid, right, left);
    }



}
