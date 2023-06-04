package com.gqs.algorithm.class016_monotonousStack;

import java.util.Stack;

// 测试链接：https://leetcode.com/problems/maximal-rectangle/
public class T004_MaximalRectangle {

    public static int maximalRectangle(char[][] map){
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        // 定义直方图
        int[] height = new int[map[0].length];
        // 遍历二维数组
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // 判断当前位置是0则值为0，否则增加直方图高度
                height[j] = map[i][j] == '0' ? 0 : height[j] + 1;
            }
            // 获取当前最大直方图长度，和上一题一样
            maxArea = Math.max(maxArea, largestRectangleArea(height));
        }
        return maxArea;
    }

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
