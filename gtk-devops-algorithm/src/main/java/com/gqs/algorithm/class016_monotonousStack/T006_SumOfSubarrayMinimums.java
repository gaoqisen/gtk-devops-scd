package com.gqs.algorithm.class016_monotonousStack;

// 测试链接：https://leetcode.com/problems/sum-of-subarray-minimums/
public class T006_SumOfSubarrayMinimums {

    /**
     * 获取所有子数组最小值的累加和
     *
     * @param arr
     * @return
     */
    public int sumSubarrayMins(int[] arr) {
        int[] stack = new int[arr.length];
        // i位置左边临近最小值位置
        int[] left = nearLessLeft(arr, stack);
        // i位置右边临近最小值位置
        int[] right = nearLessRight(arr, stack);
        long num = 0;
        for (int i = 0; i < arr.length; i++) {
            // 左边的位置数量
            int start = i - left[i];
            // 右边的位置数量
            int end = right[i] - i;
            // 累加当前位置所有子数组最小值
            num += start * end * (long)arr[i];
            num %= 1000000007;
        }
        return (int)num;
    }

    // 用同等长度的数组记录每个位置右边临近最小值的位置
    private static int[] nearLessRight(int[] arr, int[] stack) {
        int n = arr.length;
        int[] right = new int[n];
        int size = 0;
        for (int i = 0; i < n; i++) {
            while (size != 0 && arr[i] < arr[stack[size - 1]]) {
                right[stack[--size]] = i;
            }
            // 将当前值放入栈
            stack[size++] = i;
        }
        // 最小值就是最右边的值
        while (size != 0) {
            right[stack[--size]] = n;
        }
        return right;
    }

    // 用同等长度的数组记录每个位置左边临近最小值的位置
    private static int[] nearLessLeft(int[] arr, int[] stack) {
        int n = arr.length;
        int[] left = new int[n];
        int size = 0;
        // 从右往左进行对比
        for (int i = n - 1; i >= 0; i--) {
            // 当前位置不对比，当前位置小于等于前一个位置的值，则前一个位置最小值就是当前位置。
            while (size !=0 && arr[i] <= arr[stack[size - 1]]) {
                left[stack[--size]] = i;
            }
            // 将当前值放入栈
            stack[size++] = i;
        }
        // 左边没有最小值
        while (size != 0) {
            left[stack[--size]] = -1;
        }
        return left;
    }


}
