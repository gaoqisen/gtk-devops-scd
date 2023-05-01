package com.gqs.algorithm.class015_slidingWindow;

import java.util.LinkedList;

public class T001_SlidingWindow {


    public static int[] getMaxWindowNum(int[] arr, int w) {
        if(arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int n = arr.length;
        int[] res = new int[n - w + 1];
        // 存储最大值的下标
        LinkedList<Integer> doubleQueue = new LinkedList<>();
        int index = 0;
        for (int i = 0; i < n; i++) {
            // 弹出队列里面值小于等于当前值的元素
            while (!doubleQueue.isEmpty() && arr[doubleQueue.peekLast()] <= arr[i]) {
                doubleQueue.pollLast();
            }
            // 往双端队列后面添加值
            doubleQueue.addLast(i);

            // 窗口左边往右移动
            if(!doubleQueue.isEmpty() && doubleQueue.peekFirst() == i - w) {
                doubleQueue.pollFirst();
            }
            // 窗口滑动到w位置开始计数(窗口：0 ～ w)
            if(i >= w - 1 && !doubleQueue.isEmpty()) {
                res[index++] = arr[doubleQueue.peekFirst()];
            }
        }
        return res;
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
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

    // 暴力的对数器方法
    public static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindowNum(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


}
