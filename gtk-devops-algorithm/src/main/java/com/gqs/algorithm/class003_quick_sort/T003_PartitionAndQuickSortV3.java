package com.gqs.algorithm.class003_quick_sort;

import com.alibaba.fastjson.JSONObject;

// 分区快排
public class T003_PartitionAndQuickSortV3 {

    public static void main(String[] args) {
        int[] arr = {2,6,9,3,1,0,10,45,5};
        sort(arr);
        System.out.println(JSONObject.toJSONString(arr));
    }

    private static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r){
        if(l >= r) {
            return;
        }
        // 和第二个版本对比只是多了一个随机数去定位基准数
        swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        int[] m = partition(arr, l, r);
        process(arr, l, m[0] - 1);
        process(arr, m[1] + 1, r);
    }

    public static int[] partition(int[] arr, int l, int r) {
        if(l == r) {
            return new int[]{l, r};
        }

        if(l > r) {
            return new int[]{-1, -1};
        }

        int leftArea = l -1;
        int rightArea = r;
        int index = l;
        while (index < rightArea) {
            if(arr[index] == arr[r]) {
                index++;
                continue;
            }
            if(arr[index] < arr[r]) {
                swap(arr, index++, ++leftArea);
                continue;
            }
            swap(arr, index, --rightArea);
        }
        swap(arr, rightArea, r);
        return new int[]{leftArea + 1, rightArea};
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
