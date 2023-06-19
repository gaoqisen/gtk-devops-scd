package com.gqs.algorithm.class_020_bfprt;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

// 获取列表中第k小的数
public class T001_FindMinKth {


    public static int minKth(int[] array, int k) {
        // copy一下数据，后面对比时使用
        int[] arr = copyArray(array);
        return process(arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i != ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    private static int process(int[] arr, int l, int r, int index){
        // 左边和右边的下标相等的时候，下标就是index
        if(l == r) {
            return arr[l];
        }

        // 随机一个值用于中间数字
        int pivot = arr[l + (int) Math.random() * (r - l + 1)];
        // 荷兰国旗问题， 获取数组pivot的左边界和右边界的值
        int[] range = partition(arr, l, r, pivot);
        // 当前值就在中间，直接返回当前值
        if(index >= range[0] && index <= range[1]) {
            return arr[index];
        }
        // 当前值在左边
        else if(index < range[0]){
            return process(arr, l, range[0] - 1, index);
        }
        // 当前值在右边
        else {
            return process(arr, range[1] + 1, r, index);
        }
    }

    // 进行分区，小于m的值放左边，大于m的值放右边，等于的放中间。 返回中间值的左右边界
    public static int[] partition(int[] arr, int l, int r, int m) {
        int less = l - 1;
        int more = r + 1;
        int cur = l;
        // 从左往右比较，左下标和右下标遇上是结束
        while (cur < more) {
            if(arr[cur] < m) {
                // 当前值小于中间值则当前值替换后往后扩
                swap(arr, cur++, ++less);
            } else if(arr[cur] > m) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }



    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }


    public static class MaxHeapComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }

    }

    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth(arr, k);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
