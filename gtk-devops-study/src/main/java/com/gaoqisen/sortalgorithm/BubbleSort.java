package com.gaoqisen.sortalgorithm;

// 冒泡排序
public class BubbleSort {

    public static void main(String[] args) {
        int[] a = {9,6,5,2,3,4,80,7,1,10};
        for (int i = 0; i < a.length; i++) {
            for (int j = 1; j < a.length; j++) {
                int index = j-1;
                if(a[index] > a[j]) {
                    swap(a, index, j);
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
