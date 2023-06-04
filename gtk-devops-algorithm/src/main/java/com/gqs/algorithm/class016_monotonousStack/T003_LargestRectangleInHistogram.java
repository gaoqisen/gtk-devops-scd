package com.gqs.algorithm.class016_monotonousStack;

import java.util.Stack;

// https://leetcode.cn/problems/largest-rectangle-in-histogram/submissions/
public class T003_LargestRectangleInHistogram {

    /**
     * 获取数组中最大矩形面积
     *
     * @param height 数组值
     * @return 最大的面积
     */
    public static int largestRectangleArea(int[] height) {
        if(height == null || height.length < 1){
            return 0;
        }
        int n = height.length;
        Stack<Integer> stack = new Stack<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                // 当前位置右边临近值下标
                Integer pop = stack.pop();
                // 当前位置左边临近值下标
                int leftLeftIndex = stack.isEmpty() ? -1 : stack.peek();
                // （右边下标-左边下标）* 当前高度，就是当前位置的最大面积
                int curArea = (i - leftLeftIndex - 1) * height[pop];
                max = Math.max(max, curArea);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            // 当前位置右边临近值下标
            Integer pop = stack.pop();
            // 当前位置左边临近值下标
            int leftLeftIndex = stack.isEmpty() ? -1 : stack.peek();
            // （右边下标-左边下标）* 当前高度，就是当前位置的最大面积
            int curArea = (n - leftLeftIndex - 1) * height[pop];
            max = Math.max(max, curArea);
        }
        return max;
    }


}
