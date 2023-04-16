package com.gqs.algorithm.class014_dp;

// 两个人抽卡，能获取的最多分数
public class T003_CardsInLine {



    public static void main(String[] args) {
        int[] arr = { 7,4,16,15,1 };
        System.out.println(win(arr));
        System.out.println(win1(arr));
        System.out.println(win2(arr));

    }

    public static int win(int[] arr) {
        // 先手获取的最大值
        int b = before(arr, 0, arr.length -1);
        // 后手获取的最大值
        int a = after(arr, 0, arr.length-1);
        return Math.max(a, b);
    }

    // 先拿有两种情况，1、拿左边的值+ 后手获取的最大值。 2、拿右边的值 + 后手获取的最大值
    public static int before(int[] arr, int left, int right) {
        // 最后一张牌直接获取
        if(left == right) {
            return arr[left];
        }
        // 先拿左边的 + 对手拿了之后剩余的后手（left+1）
        int l = arr[left] + after(arr, left + 1, right);
        // 先拿右边的 + 对手拿了之后剩余的后手（right-1）
        int r = arr[right] + after(arr, left, right -1);
        return Math.max(l, r);
    }

    // 后拿
    public static int after(int[] arr, int left, int right) {
        // 只有一张牌，无法获取
        if(left == right) {
            return 0;
        }
        // 拿左边的先手值（对手拿了右边的值）
        int l = before(arr, left, right - 1);
        // 拿右边的先手值（对手拿走了左边的值）
        int r = before(arr,left + 1, right);
        // 获取最小的值
        return Math.min(l, r);
    }



    public static int win1(int[] arr) {
        int n = arr.length;
        int[][] beforeDp = new int[n][n];
        int[][] afterDp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                beforeDp[i][j] = -1;
                afterDp[i][j] = -1;
            }
        }


        int b = beforeCache(arr, 0, arr.length -1, beforeDp, afterDp);
        int a = afterCache(arr, 0, arr.length-1, beforeDp, afterDp);
        return Math.max(a, b);
    }

    // 先拿有两种情况，1、拿左边的值+ 后手获取的最大值。 2、拿右边的值 + 后手获取的最大值
    public static int beforeCache(int[] arr, int left, int right, int[][] beforeDp, int[][] afterDp) {
        if(beforeDp[left][right] != -1) {
            return beforeDp[left][right];
        }
        // 最后一张牌直接获取
        int result = 0;
        if(left == right) {
            result = arr[left];
        } else {
            // 先拿左边的 + 对手拿了之后剩余的后手（left+1）
            int l = arr[left] + afterCache(arr, left + 1, right, beforeDp, afterDp);
            // 先拿右边的 + 对手拿了之后剩余的后手（right-1）
            int r = arr[right] + afterCache(arr, left, right -1, beforeDp, afterDp);
            result = Math.max(l, r);
        }
        beforeDp[left][right] = result;
        return result;
    }

    // 后拿
    public static int afterCache(int[] arr, int left, int right, int[][] beforeDp, int[][] afterDp) {
        if(afterDp[left][right] != -1) {
            return afterDp[left][right];
        }
        // 只有一张牌，无法获取
        int result = 0;
        if(left != right) {
            // 拿左边的先手值（对手拿了右边的值）
            int l = beforeCache(arr, left, right - 1, beforeDp, afterDp);
            // 拿右边的先手值（对手拿走了左边的值）
            int r = beforeCache(arr,left + 1, right, beforeDp, afterDp);
            // 获取最小的值
            result = Math.min(l, r);
        }
        afterDp[left][right] = result;
        return result;
    }

    public static int win2(int[] arr) {
        if(arr == null || arr.length < 1) {
            return 0;
        }
        int n = arr.length;
        // 初始化after的有效值
        int[][] beforeDp = new int[n][n];
        int[][] afterDp = new int[n][n];
        for (int i = 0; i < n; i++) {
            beforeDp[i][i] = arr[i];
        }

        for (int i = 1; i < n; i++) {
            int left = 0;
            int right = i;
            // 行不会越界，只需要判断列即可
            while (right < n) {
                // 之前从递归中取，缓存从dp中取
                System.out.println( arr[left]);
                System.out.println(afterDp[left + 1][right]);
                beforeDp[left][right] = Math.max(
                        // 先拿左边的 + 对手拿了之后剩余的后手（left+1）
                        arr[left] + afterDp[left + 1][right],
                        // 先拿右边的 + 对手拿了之后剩余的后手（right-1）
                        arr[right] + afterDp[left][right -1]);

                // 获取最小的值
                afterDp[left][right] = Math.min(
                        // 拿左边的先手值（对手拿了右边的值）
                        beforeDp[left][right - 1],
                        // 拿右边的先手值（对手拿走了左边的值）
                        before(arr,left + 1, right));
                left++;
                right++;
            }
        }
        return Math.max(
                // 先手获取的最大值
                beforeDp[0][arr.length -1],
                // 后手获取的最大值
                afterDp[0][arr.length-1]);
    }


}
