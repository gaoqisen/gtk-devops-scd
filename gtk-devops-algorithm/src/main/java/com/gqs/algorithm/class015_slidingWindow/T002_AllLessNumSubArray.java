package com.gqs.algorithm.class015_slidingWindow;

import java.util.LinkedList;

public class T002_AllLessNumSubArray {

    public static int num(int[] arr, int sum) {
        if(arr == null || arr.length < 1 || sum < 0) {
            return 0;
        }

        int n = arr.length;
        LinkedList<Integer> maxQueue = new LinkedList<>();
        LinkedList<Integer> minQueue = new LinkedList<>();
        // 记录总次数
        int count = 0;
        // 记录右边的下标
        int right = 0;
        for (int left = 0; left < n; left++) {
            // 右下标不能越界
            while (right < n) {
                // 弹出队列右边的值(队列中小于等于当前元素的值弹出)
                while (!maxQueue.isEmpty() && arr[maxQueue.peekLast()] <= arr[right]) {
                    maxQueue.pollLast();
                }
                maxQueue.addLast(right);
                // 弹出队列右边的值(队列中大于等于当前元素的值弹出)
                while (!minQueue.isEmpty() && arr[minQueue.peekLast()] >= arr[right]) {
                    minQueue.pollLast();
                }
                minQueue.addLast(right);

                // 判断当前窗口最大值-最小值 <= sum
                if(!maxQueue.isEmpty() && !minQueue.isEmpty()
                        && arr[maxQueue.peekFirst()] - arr[minQueue.peekFirst()] <= sum) {
                    right++;
                }
                // 不达标则结束遍历
                else {
                    break;
                }
            }
            // 计算当前窗口下面的所有子数组都达标
            count += right - left;
            // 左边的值往右移动（过期处理）
            if(!maxQueue.isEmpty() && maxQueue.peekFirst() == left) {
                maxQueue.pollFirst();
            }
            if(!minQueue.isEmpty() && minQueue.peekFirst() == left) {
                minQueue.pollFirst();
            }
        }
        return count;
    }



    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }

    // 暴力的对数器方法
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }
}
