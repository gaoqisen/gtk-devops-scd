package com.gqs.algorithm.class016_monotonousStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class T001_MonotonousStack {

    /**
     * 获取临近位置最小的值（没有重复值）
     *
     * @param arr 原始数组
     * @return 数组左右临近最小的值
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        if(arr == null || arr.length < 1) {
            return null;
        }
        int n = arr.length;
        int[][] res = new int[n][2];

        // 创建栈，由小到大
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            // 放入的值大于栈里面最大值则弹出，弹出时候记录弹出值的左右最小值
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int val = stack.pop();
                // 左边值就是弹出后栈里面最大的值
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[val][0] = leftLessIndex;
                // 右边值就是当前放入的值
                res[val][1] = i;
            }
            stack.push(i);
        }
        // 弹出栈里面其他值
        while (!stack.isEmpty()) {
            // 弹出当前值
            int val = stack.pop();
            // 左边的值就是栈里面的最大值
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            res[val][0] = leftLessIndex;
            // 没有放入的值，则右边的值没有
            res[val][1] = -1;
        }
        return res;
    }

    /**
     * 获取临近位置最小的值（有重复值）
     *
     * @param arr 原始数组
     * @return 数组左右临近最小的值
     */
    public static int[][] getNearLessRepeat(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        int n = arr.length;
        int[][] res = new int[n][2];
        // 创建栈，由小到大.重复值则放到list里面
        Stack<List<Integer>> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            // 如果当前值小于栈顶值，则弹出值并记录弹出值的左右临近最小位置
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> pop = stack.pop();
                // 左边的值就是数组最后面的值
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer val : pop) {
                    // 重复值左边临近位置最小的值都是相同的
                    res[val][0] = leftLessIndex;
                    // 右边的临近位置最小值就是当前放入值
                    res[val][1] = i;
                }
            }

            // 判断如果当前值和栈里面的元素相同则放到当前栈里，否则在栈里面添加值
            if(!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(i);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }
        // 处理栈里面剩余的值
        while (!stack.isEmpty()) {
            List<Integer> pop = stack.pop();
            // 左边的值就是数组最后面的值
            int leftLeftIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer val : pop) {
                res[val][0] = leftLeftIndex;
                res[val][1] = -1;
            }
        }
        return res;
    }

    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops! getNearLessNoRepeat");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLessRepeat(arr2), rightWay(arr2))) {
                System.out.println("Oops! getNearLessRepeat");
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }


}
