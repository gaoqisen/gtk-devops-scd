package com.gqs.algorithm.other;

// 插入排序
public class InsertionSort {

    public static void main(String[] args) {
        int[] a = {9,6,5,2,3,4,80,7,1,10};

        for (int i = 0; i < a.length; i++) {
            // 通过当前值和前面的值比较，并把当前值放到恰当的位置
            for (int j = i; j > 0; j--) {
                if(a[j] < a[j-1]) {
                    swap(a, j, j-1);
                }
            }
        }

        print(a);

    }

    static void swap(int[] a, int start, int end) {
        int temp = a[start];
        a[start] = a[end];
        a[end] = temp;
    }
    static void print(int[] a) {
        // 输出
        for (int i = 0; i < a.length; i++) {
            if(i == (a.length - 1)){
                System.out.print(a[i]);
                break;
            }
            System.out.print(a[i]+ ", ");
        }
        System.out.println("");
    }

}
