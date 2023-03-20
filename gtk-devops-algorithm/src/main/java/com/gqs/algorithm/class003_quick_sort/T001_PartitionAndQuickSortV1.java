package com.gqs.algorithm.class003_quick_sort;

import com.alibaba.fastjson.JSONObject;

// 分区快排
public class T001_PartitionAndQuickSortV1 {

    public static void main(String[] args) {
        int[] arr = {2,6,9,3,1,0,10,45,5};
        sort(arr);
        System.out.println(JSONObject.toJSONString(arr));
    }

    private static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }

        process(arr, 0, arr.length -1);
    }

    private static void process(int[] arr, int l, int r) {
        if(l >= r) {
            return;
        }

        int m = partition(arr, l, r);
        process(arr, l, m - 1);
        process(arr, m + 1, r);
    }

    private static int partition(int[] arr, int l, int r) {
        if(l > r) {
            return -1;
        }
        if(l == r) {
            return l;
        }
        int current = l;
        int leftArea = l-1;
        while (current < r) {
            if(arr[current] <= arr[r]) {
                swap(arr, current, ++leftArea);
            }
            current++;
        }
        swap(arr, r, ++leftArea);
        return leftArea;
    }

    private static void swap(int[] arr, int i, int j) {
        if(i == j) {
            return;
        }
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
