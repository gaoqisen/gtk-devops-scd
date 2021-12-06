package com.gaoqisen.sortalgorithm;

/**
 * 冒泡排序
 */
public class Day01BubbleSort {

    public static void main(String[] args) {
        int[] arr = {1,3,9,6,8,9,4,2,6,7};
        sort(arr);
        for(int i : arr) {
            System.out.println(i);
        }
    }

    private static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length; j++) {
                // 用前一个和和后一个进行比较
                int current = arr[j - 1];
                if(current > arr[j]) {
                    swap1(arr, j - 1, j);
                }
            }
        }
    }

    private static void swap1(int[] arr, int i, int j) {
        // 通过异或运行进行数组值的替换（不用申请额外的空间， 但是需要两个引用指向的内存不同，否则会将数据变为0）
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    private static void swap(int[] arr, int i, int i1) {
        int index = arr[i];
        arr[i] = arr[i1];
        arr[i1] = index;
    }

}
