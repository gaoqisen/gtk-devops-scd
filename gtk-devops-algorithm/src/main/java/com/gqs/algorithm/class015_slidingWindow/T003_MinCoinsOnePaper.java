package com.gqs.algorithm.class015_slidingWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class T003_MinCoinsOnePaper {

    /**
     * 获取面值数组到目标值的最小张数
     *
     * 方案一：动态规划
     *
     * @param arr 面值数组
     * @param aim 目标值
     * @return 最小张数
     */
    public static int getMinZhangDp(int[] arr, int aim) {
        if(aim < 1) {
            return 0;
        }
        // 去重
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int n = coins.length;
        int[][] dp = new int[n + 1][aim + 1];

        // 基础条件赋值
        dp[n][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[n][i] = Integer.MAX_VALUE;
        }
        // 遍历下标
        for (int index = n - 1; index >= 0 ; index--) {
            // 遍历剩余钱数
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                // 遍历张数一直到不超过目标值，以及达到张数最大值
                for (int zhang = 1; zhang * coins[index] <= aim && zhang <= zhangs[index]; zhang++) {
                    // 剩余的钱不够了
                    if(rest - zhang * coins[index] < 0) {
                        continue;
                    }
                    // 值无效
                    if(dp[index + 1][rest - zhang * coins[index]] == Integer.MAX_VALUE) {
                        continue;
                    }
                    // 取最小值进行赋值
                    dp[index][rest] = Math.min(dp[index][rest],  zhang + dp[index + 1][rest - zhang * coins[index]]);
                }
            }
        }
        return dp[0][aim];
    }

    /**
     * 获取面值数组到目标值的最小张数
     *
     * 方案二：优化版本动态规划+滑动窗口（解决枚举行为）
     *
     * @param arr 面值数组
     * @param aim 目标值
     * @return 最小张数
     */
    public static int getMinZhangDpWindow(int[] arr, int aim) {
        if(aim < 1) {
            return 0;
        }
        // 去重
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int n = coins.length;
        int[][] dp = new int[n + 1][aim + 1];

        // 基础条件赋值
        dp[n][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[n][i] = Integer.MAX_VALUE;
        }

        // 利用窗口内最小值更新结构
        for (int i = n - 1; i >= 0; i--) {
            // 分组遍历面值
            for (int mod = 0; mod < Math.min(aim + 1, coins[i]); mod++) {
                LinkedList<Integer> w = new LinkedList<>();
                w.add(mod);
                dp[i][mod] = dp[i + 1][mod];

                // 面值累加往后（例：当前面值3，则3，6，9）
                // 当前面值x： r = mod + x， mod + 2*x， mod + 3*x
                for (int r = mod + coins[i]; r <= aim ; r += coins[i]) {
                    // 最小值的窗口更新结构, 右边的值往后走
                    while (!w.isEmpty() && (
                            // 无效值
                            dp[i + 1][w.peekLast()] == Integer.MAX_VALUE ||
                            // 有效则加上补偿值后大于等于后面的值
                            dp[i + 1][w.peekLast()] + compensate(w.peekLast(), r, coins[i]) >= dp[i + 1][r]
                            )) {
                        w.pollLast();
                    }
                    w.addLast(r);

                    // 过期的数据左边的值往后走
                    int overdue = r - coins[i] * (zhangs[i] + 1);
                    if(overdue == w.peekFirst()) {
                        w.pollFirst();
                    }
                    // 赋值
                    dp[i][r] = dp[i + 1][w.peekFirst()] + compensate(w.peekFirst(), r, coins[i]);
                }
            }
        }

        return dp[0][aim];
    }

    // 补偿
    public static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }


        /**
         * 通过面值数组去重，获取info信息
         *
         * @param arr 面值数组
         * @return info数据
         */
    private static Info getInfo(int[] arr) {
        // 用map存放数量以及面值
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        // 遍历map将面值和数量拆分数组里面
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }

    private static class Info{
        private int[] coins;
        private int[] zhangs;

        public Info(int[] coins, int[] zhangs) {
            this.coins = coins;
            this.zhangs = zhangs;
        }
    }



    // -------------  测试代码

    // 为了测试
    public static int[] randomArray(int N, int maxValue) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = process(arr, index + 1, rest - arr[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            return Math.min(p1, p2);
        }
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = getMinZhangDp(arr, aim);
            int ans3 = getMinZhangDpWindow(arr, aim);
            if (ans1 != ans2 || ans3 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("功能测试结束");

        System.out.println("==========");

        int aim = 0;
        int[] arr = null;
        long start;
        long end;
        int ans2;
        int ans3;

        System.out.println("性能测试开始");
        maxLen = 30000;
        maxValue = 20;
        aim = 60000;
        arr = randomArray(maxLen, maxValue);

        start = System.currentTimeMillis();
        ans2 = getMinZhangDp(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        ans3 = getMinZhangDpWindow(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");

        System.out.println("===========");

        System.out.println("货币大量重复出现情况下，");

        System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
        System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
        System.out.println("dp3的优化用到了窗口内最小值的更新结构");
    }

}
