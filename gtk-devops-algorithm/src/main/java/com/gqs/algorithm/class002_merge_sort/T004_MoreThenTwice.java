package com.gqs.algorithm.class002_merge_sort;

import com.alibaba.fastjson.JSONObject;

/**
 * 大于两倍的数
 */
public class T004_MoreThenTwice {

    public static int sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r){
        if(l == r) {
            return 0;
        }
        int m = l + ((r - l) >> 1);
        return process(arr, l, m) + process(arr, m + 1, r) + marge(arr, l, m, r);
    }

    private static int marge(int[] arr, int l, int m, int r) {
        // 遍历左边的值，用左边的值和右边的值进行比较.
        // 将比较的逻辑抽离出来，放在下面的辅助数组逻辑里面逻辑会复杂很多。 这里最主要的优势是不回退
        int res = 0;
        int rightIndex = m + 1;
        for (int i = l; i <= m; i++) {
            // 数组从左往右递增，rightIndex值的2倍小于等于i的值时结束
            while (rightIndex <= r && (long)arr[i] > (long)(arr[rightIndex] * 2L)) {
                rightIndex++;
            }
            res += rightIndex - m -1;
        }

        // 归并排序的逻辑
        int[] help = new int[r - l + 1];
        int i = 0;
        int lIndex = l;
        int rIndex = m + 1;
        while (lIndex <= m && rIndex <= r) {
            // 那个小于就先将值放入help
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
        return res;
    }

    // for test
    public static void main(String[] args) {

        int[] arr = {8, 2, 4, 9, 0, 1, 5};
        sort(arr);
        System.out.println(JSONObject.toJSONString(arr));

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int sort = sort(arr1);
            int comparator = comparator(arr2);
            if (sort != comparator) {
                System.out.println("Oops!" + sort + ",  " + comparator);
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }



    // for test
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }



}
