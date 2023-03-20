package com.gqs.algorithm.class004_heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class T004_HeapMaxLineSegment {

    /**
     * 1、先将线段的开始位置从小到大排序
     * 2、利用小根堆，将小根堆中的最小值 小于等于 当前线段start的值弹出
     * 3、将线段的end值放入大根堆中
     */
    public static int maxLineSegmentSum(int[][] arr) {
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int max = 0;
        for (int[] ints : arr) {
            if(!queue.isEmpty() && queue.peek() <= ints[0]) {
                queue.poll();
            }
            queue.add(ints[1]);
            max = Math.max(max, queue.size());
        }
        return max;
    }


    public static void main(String[] args) {
        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxLineSegmentSum(lines);
            int ans2 = maxCover1(lines);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }

    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    // for test
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

}
