package com.gqs.algorithm.class002_merge_sort;

import com.alibaba.fastjson.JSONObject;


/**
 * 小和: 数组中一个数左边比它小的数的总和
 */
public class T002_SmallSum {

    public static void main(String[] args) {
        int[] arr = {2,6,9,3,1,0,10,45,5};
        int sum = sort(arr);
        System.out.println(sum);
        System.out.println(JSONObject.toJSONString(arr));
    }

    // 排序
    public static int sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    // 给定数组的指定l～r排序
    private static int process(int[] arr, int l, int r){
        if(l == r) {
            return 0;
        }
        int m = l + (r - l) / 2;
        return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r);
    }

    // 合并排序
    private static int merge(int[] arr, int l, int m, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l,p2 = m + 1,index = 0,res = 0;
        while (p1 <= m && p2 <= r) {
            // 如果左边的小于右边的，则通过下标换算(r - p2 +1)可以得到右边的数有多少个大于当前数(数组已经排序了)的 然后在 * arr[p1]
            res += arr[p1] < arr[p2] ? (r - p2 +1) * arr[p1] : 0;
            help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= m) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res;
    }
}
