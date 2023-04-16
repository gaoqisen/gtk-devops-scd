package com.gqs.algorithm.class014_dp;

import java.util.HashMap;
import java.util.Map;

public class T014_CoinsWaySameValueSamePapper {


    // 主函数：从0开始往后递归处理的所有方法数就是所有方法数
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        return process(info.coins, info.nums, 0, aim);
    }

    /**
     * 获取最多方法数
     *
     * @param coins 面值数组
     * @param nums 数量数组
     * @param i 当前处理位置
     * @param rest 剩余金额
     * @return 方法数
     */
    private static int process(int[] coins, int[] nums, int i, int rest) {
        if(i == coins.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        // 面值小于等于剩余金额 与 张数小于等于总张数
        for (int zhang = 0; zhang * coins[i] <= rest && zhang <= nums[i]; zhang++) {
            // 递归处理下一张面值
            ways += process(coins, nums, i + 1, rest - (zhang * coins[i]));
        }
        return ways;
    }

    // 通过面值获取info数据
    private static Info getInfo(int[] arr) {
        // 将相同面值的统计出现的次数
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int i : arr) {
            if(!counts.containsKey(i)) {
                counts.put(i, 1);
            } else {
                counts.put(i, counts.get(i) + 1);
            }
        }
        // 将面额放入coins，张数放入nums
        int n = counts.size();
        int[] coins = new int[n];
        int[] nums = new int[n];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            nums[index++] = entry.getValue();
        }
        return new Info(coins, nums);
    }

    /**
     * 动态规划处理
     *
     * @param arr 面值数据
     * @param aim 目标金额
     * @return 总数量
     */
    public static int dp(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;
        int n = nums.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                // 面值小于等于剩余金额 与 张数小于等于总张数
                for (int zhang = 0; zhang * coins[index] <= rest && zhang <= nums[index]; zhang++) {
                    // 递归处理下一张面值
                    ways += dp[index + 1][rest - (zhang * coins[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }


    /**
     * 动态规划处理（优化处理）空间压缩
     *
     * @param arr 面值数据
     * @param aim 目标金额
     * @return 总数量
     */
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] nums = info.nums;
        int n = nums.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                // 未越界
                if(rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                // 判断是否存在重复值，存在则减掉
                if(rest - coins[index] * (nums[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - coins[index] * (nums[index] + 1)];
                }
            }
        }
        return dp[0][aim];
    }

    private static class Info{
        // 面值数组
        private int[] coins;
        // 面值张数数组
        private int[] nums;

        public Info(int[] coins, int[] nums) {
            this.coins = coins;
            this.nums = nums;
        }
    }


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
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

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp(arr, aim);
            int ans3 = dp1(arr, aim);
            if (ans1 != ans2
                   || ans1 != ans3
            ) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
