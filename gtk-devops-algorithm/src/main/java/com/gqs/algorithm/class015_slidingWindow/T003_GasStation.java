package com.gqs.algorithm.class015_slidingWindow;

import java.util.LinkedList;

/**
 * https://leetcode.com/problems/gas-station
 */
public class T003_GasStation {

    /**
     * 获取一种从当前路径出发能跑完所有加油站的节点
     *
     * @param gas  加油站里能加到的油
     * @param cost 到加油站需要消耗的油
     * @return 能跑完所有加油站的节点
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] res = goodArray(gas, cost);
        // 直接获取第一个值返回
        for (int i = 0; i < gas.length; i++) {
            if(res[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 通过滑动窗口获取所有节点是否能跑完所有的加油站
     *
     * @param g 加油站能加到的油
     * @param c 到加油站需要消耗的油
     * @return 所有节点是否能跑完所有加油站
     */
    public static boolean[] goodArray(int[] g, int[] c) {
        int n = g.length;
        // 长度乘以2
        int m = n << 1;
        int[] arr = new int[m];

        // 初始化数组的值，当前能加到的油减去消耗的为负数则不可能跑完所有加油站
        for (int i = 0; i < n; i++) {
            arr[i] = g[i] - c[i];
            arr[i + n] = g[i] - c[i];
        }
        // 计算累加和并赋值
        for (int i = 1; i < m; i++) {
            arr[i] += arr[i - 1];
        }

        // 利用双端队列获取最小值
        LinkedList<Integer> w = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }

        boolean[] ans = new boolean[n];
        for (int offset = 0, i = 0, j = n; j < m; offset = arr[i++], j++) {
            // 蚝油不为0，最小的薄弱点不为负数则可以跑完
            if(arr[w.peekFirst()] - offset >= 0) {
                ans[i] = true;
            }
            // 窗口左边过期
            if(w.peekFirst() == i) {
                w.pollFirst();
            }
            // 窗口右边过期，队列里面不是最小的值则弹出
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            // 往队列里面添加最小值下标
            w.addLast(j);
        }
        return ans;
    }


}
