package com.gqs.algorithm.class005_count_sort;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * 基数排序（桶排序）
 */
public class T003_CountSort {

    public static void main(String[] args) {
        int[] arrTest = new int[]{2,6,8,5,4,2,1,9};
        sort(arrTest);
        System.out.println(JSONObject.toJSONString(arrTest));


        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 150;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            sort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        sort(arr);
        printArray(arr);

    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        int max = 0;
        for (int j : arr) {
            max = Math.max(max, j);
        }

        int[] bucket = new int[max + 1];
        for (int i : arr) {
            bucket[i]++;
        }

        int j = 0;
        for (int i = 0; i < bucket.length; i++) {
            int count = bucket[i];
            if(count < 1) {
                continue;
            }
            while (count > 0) {
                arr[j++] = i;
                count--;
            }
        }
    }



    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
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
