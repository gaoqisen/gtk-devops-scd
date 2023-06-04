package com.gqs.algorithm.class016_monotonousStack;

import java.util.Stack;

// 测试链接 : https://leetcode.com/problems/maximum-subarray-min-product/
public class T002_AllTimesMinToMax {


    public static int maxSumMinProduct(int[] arr) {
        if(arr == null || arr.length < 1) {
            return 0;
        }
        // 计算累加和
        int n = arr.length;
        int[] sums = new int[n];
        sums[0] = arr[0];
        for (int i = 1; i < n; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }

        // 利用单调栈获取临近位置最小值
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                Integer pop = stack.pop();
                max = Math.max(max,
                        (stack.isEmpty() ?
                            // 左边没有比当前值小的
                            sums[i - 1] :
                            // 左边由比当前值小的，则右边的累加和减去左边的累加和
                            sums[i - 1] - sums[stack.peek()])
                        // 乘以最小值
                        * arr[pop]);
            }
            stack.push(i);
        }
        // 栈里面存在值
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            max = Math.max(max,
                    (stack.isEmpty() ?
                            // 左边没有比当前值小的
                            sums[n - 1] :
                            // 左边由比当前值小的，则右边的累加和减去左边的累加和
                            sums[n - 1] - sums[stack.peek()])
                            // 乘以最小值
                            * arr[pop]);
        }

        return max;
    }


}
