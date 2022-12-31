package com.gqs.algorithm.other;

// 选择排序
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {8,6,5,4,7,1,2,3,2};

        // 排序算法，每次找到最小的数字放在最前面
        for (int i = 0; i< arr.length - 1; i++) {
            int minVal = i;
            // 找到最小数字的下标
            for(int j = i+1; j < arr.length; j++) {
                if(arr[j] < arr[minVal]) {
                    minVal = j;
                }
            }

            // 将最小的数字放在最前面
            int tmp = arr[minVal];
            arr[minVal] = arr[i];
            arr[i] = tmp;
        }

        // 遍历数组
        for (int i = 0; i< arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
