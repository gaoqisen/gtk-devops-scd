package com.gqs.algorithm.class003;

import com.alibaba.fastjson.JSONObject;

// 分区快排
public class T002_PartitionAndQuickSortV2 {

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

        int[] m = partition(arr, l, r);
        process(arr, l, m[0] - 1);
        process(arr, m[1] + 1, r);
    }

    // 荷兰数组，小于 等于 大于。 返回中间值的边界
    private static int[] partition(int[] arr, int l, int r) {
        if(l > r) {
            return new int[]{-1, -1};
        }
        if(l == r) {
            return new int[]{l, r};
        }
        int leftArea = l - 1;
        int rightArea = r;
        int index = l;
        while (index < rightArea) {
            // 当前值等于右边的基准值则当前值往后移
            if(arr[index] == arr[r]) {
                index++;
                continue;
            }
            // 当前值小于右边的基准值则左区域值+1后和当前值替换，然后当前值+1
            if(arr[index] < arr[r]) {
                swap(arr, ++leftArea, index++);
                continue;
            }
            // 当前值大于右边的基准值则当前值和右区域往左移后替换，替换后当前值重新比
            swap(arr, --rightArea, index);
        }
        swap(arr, r, rightArea);
        return new int[]{leftArea, rightArea};
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
