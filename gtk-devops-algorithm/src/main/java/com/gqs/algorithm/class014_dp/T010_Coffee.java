package com.gqs.algorithm.class014_dp;

import java.util.Arrays;
import java.util.PriorityQueue;

public class T010_Coffee {


    /**
     * 获取最小咖啡杯变干净的时间
     *
     * @param arr 第i号机器泡咖啡的时间
     * @param n n个人等待泡咖啡
     * @param a 洗咖啡机耗时
     * @param b 咖啡杯自己挥发耗时
     * @return 最小咖啡杯变干净的时间
     */
    public static int minTime(int[] arr, int n, int a, int b) {
        // 利用大根堆做咖啡
        PriorityQueue<Machine> queue = new PriorityQueue<>((f, t) ->
                (f.timePoint + f.workTime) - (t.timePoint + t.workTime));
        // 初始化机器的时间和工作时间
        for (int i = 0; i < arr.length; i++) {
            queue.add(new Machine(0, arr[i]));
        }

        // n个人的喝完时间， 获取喝完最优数组（最优排对）
        int[] drink = new int[n];
        for (int i = 0; i < n; i++) {
            Machine poll = queue.poll();
            // 工作时间往后加
            poll.timePoint += poll.workTime;
            // 喝完的时间放入drink
            drink[i] = poll.timePoint;
            queue.add(poll);
        }

        // 从下标0开始，时间为0
        return bestTime(drink, a, b, 0, 0);
    }

    /**
     * 最好洗杯子时间
     *
     * @param drink n个人的喝完时间（可以开始洗的时间）
     * @param a 洗咖啡机耗时（串行）
     * @param b 咖啡杯自己挥发耗时（并行）
     * @param index 当前人数下标
     * @param free 洗咖啡机可用时间
     * @return 所有杯子都变干净的最早结束时间
     */
    private static int bestTime(int[] drink, int a, int b, int index, int free) {
        // 洗完了
        if(index == drink.length){
            return 0;
        }
        // index号杯子决定洗
        // 开始洗的时间和洗咖啡可用时间的最大时间 + 洗咖啡耗时（喝完后洗咖啡杯机器可能不空闲，咖啡机空闲但是开始洗的时间大）
        int selfClean = Math.max(drink[index], free) + a;
        int restClean = bestTime(drink, a, b, index + 1, selfClean);
        // 木桶原理（当前的时间和后面的其他杯子的时间选最大）
        int p1 = Math.max(selfClean, restClean);

        // index号杯子决定挥发
        // 喝完后就挥发（并行）
        int selfClean1 = drink[index] + b;
        int restClean1 = bestTime(drink, a, b, index + 1, free);
        // 木桶原理（当前的时间和后面的其他杯子的时间选最大）
        int p2 = Math.max(selfClean1, restClean1);

        // 获取最小的方案
        return Math.min(p1, p2);
    }

    /**
     * 获取最小咖啡杯变干净的时间
     *
     * @param arr 第i号机器泡咖啡的时间
     * @param n n个人等待泡咖啡
     * @param a 洗咖啡机耗时
     * @param b 咖啡杯自己挥发耗时
     * @return 最小咖啡杯变干净的时间
     */
    public static int minTime1(int[] arr, int n, int a, int b) {
        // 利用大根堆做咖啡
        PriorityQueue<Machine> queue = new PriorityQueue<>((f, t) ->
                (f.timePoint + f.workTime) - (t.timePoint + t.workTime));
        // 初始化机器的时间和工作时间
        for (int i = 0; i < arr.length; i++) {
            queue.add(new Machine(0, arr[i]));
        }

        // n个人的喝完时间， 获取喝完最优数组（最优排对）
        int[] drink = new int[n];
        for (int i = 0; i < n; i++) {
            Machine poll = queue.poll();
            // 工作时间往后加
            poll.timePoint += poll.workTime;
            // 喝完的时间放入drink
            drink[i] = poll.timePoint;
            queue.add(poll);
        }

        // 从下标0开始，时间为0
        return dp(drink, a, b);
    }

    /**
     * 动态规划最好洗杯子时间
     *
     * @param drink n个人的喝完时间（可以开始洗的时间）
     * @param a 洗咖啡机耗时（串行）
     * @param b 咖啡杯自己挥发耗时（并行）
     * @return 所有杯子都变干净的最早结束时间
     */
    private static int dp(int[] drink, int a, int b) {
        int n = drink.length;
        int maxFree = 0;
        for (int i = 0; i < drink.length; i++) {
            maxFree = Math.max(drink[i], maxFree) + a;
        }

        int[][] dp = new int[n + 1][maxFree + 1];
        for (int index = n - 1; index >= 0 ; index--) {
            for (int free = 0; free <= maxFree; free++) {

                // index号杯子决定洗
                // 开始洗的时间和洗咖啡可用时间的最大时间 + 洗咖啡耗时（喝完后洗咖啡杯机器可能不空闲，咖啡机空闲但是开始洗的时间大）
                int selfClean = Math.max(drink[index], free) + a;
                if(selfClean > maxFree) {
                    continue;
                }
                int restClean = dp[index + 1][selfClean];
                // 木桶原理（当前的时间和后面的其他杯子的时间选最大）
                int p1 = Math.max(selfClean, restClean);

                // index号杯子决定挥发
                // 喝完后就挥发（并行）
                int selfClean1 = drink[index] + b;
                int restClean1 = dp[index + 1][free];
                // 木桶原理（当前的时间和后面的其他杯子的时间选最大）
                int p2 = Math.max(selfClean1, restClean1);
                // 获取最小的方案
                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    // 机器
    public static class Machine{

        // 当前时间点
        public int timePoint;
        // 做一杯咖啡的时间
        public int workTime;

        public Machine(int timePoint, int workTime) {
            this.timePoint = timePoint;
            this.workTime = workTime;
        }
    }

    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }


    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime(arr, n, a, b);
            int ans3 = minTime1(arr, n, a, b);

            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 );
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }


}
