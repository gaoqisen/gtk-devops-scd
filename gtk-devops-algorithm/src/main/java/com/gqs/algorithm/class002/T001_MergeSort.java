package com.gqs.algorithm.class002;

import com.alibaba.fastjson.JSONObject;

/**
 * 归并排序
 */
public class T001_MergeSort {


    public static void main(String[] args) {
        int[] arr = {8, 2, 4, 9, 0, 1, 5};
        MergeSortTrain1.sort(arr);
        System.out.println(JSONObject.toJSONString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length -1);
    }

    private static void process(int[] arr, int left, int right) {
        if(left == right) {
            return;
        }
        // 获取数组中间值
        int middle = left + (right - left) / 2;
        // 先排左边的
        process(arr, left, middle);
        // 在排右边的
        process(arr, middle + 1, right);
        // 之后合并两边的值
        merge(arr, left, middle, right);
    }

    private static void merge(int[] arr, int left, int middle, int right) {
        // 利用辅助数组实现
        int[] help = new int[right - left + 1];
        // 辅助数组下标
        int index = 0;

        // 左边第一个下标
        int leftIndex = left;
        // 右边第一个下标
        int rightIndex = middle + 1;

        // 用左边的值和右边的所有数对比，如果谁小谁放在help里面
        while (leftIndex <= middle && rightIndex <= right) {
            help[index++] = arr[leftIndex] <= arr[rightIndex] ? arr[leftIndex++] : arr[rightIndex++];
        }

        // 左边｜右边 还有数没有比较的则直接将数据放入help
        while (leftIndex <= middle) {
            help[index++] = arr[leftIndex++];
        }
        while (rightIndex <= right) {
            help[index++] = arr[rightIndex++];
        }

        // 将辅助的数据（已排好序） 替换到原数组里面
        for (int i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
    }
}


/**
 * 训练1
 */
class MergeSortTrain1{

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r){
        if(l == r) {
            return;
        }
        int m = l + ((r - l) >> 1);
        process(arr, l, m);
        process(arr, m + 1, r);
        marge(arr, l, m, r);
    }

    private static void marge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int lIndex = l;
        int rIndex = m + 1;
        while (lIndex <= m && rIndex <= r) {
            help[i++] = arr[lIndex] <= arr[rIndex] ? arr[lIndex++] : arr[rIndex++];
        }

        while (lIndex <= m) {
            help[i++] = arr[lIndex++];
        }
        while (rIndex <= r) {
            help[i++] = arr[rIndex++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[l + j] = help[j];
        }
    }
}


/**
 * 逆序对
 */

/**
 * 数组中，元素右边的数*2还是小于该数的总和
 */
