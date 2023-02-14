package com.gqs.algorithm.class010;

import java.util.PriorityQueue;

public class T004_MaximizedCapital {

    /**
     * 获取项目最大收益
     *
     * @param k 项目次数
     * @param w 初始资金
     * @param profits 利润
     * @param capital 资金
     * @return 最大项目收益
     */
    public static int getMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // 将项目放入小根堆，按照资金从小到大
        PriorityQueue<Program> capitalQueueMin = new PriorityQueue<>((a, b) -> a.c - b.c);

        for (int i = 0; i < profits.length; i++) {
            capitalQueueMin.add(new Program(profits[i], capital[i]));
        }

        // 大根堆放利润，从大到小
        PriorityQueue<Program> profitsQueueMax = new PriorityQueue<>((a, b) -> b.p - a.p);
        for(int i = 0; i < k; i++) {
            // 有项目并且资金小于等于当前资金时，则将项目放入大根堆
            while (!capitalQueueMin.isEmpty() && capitalQueueMin.peek().c <= w) {
                profitsQueueMax.add(capitalQueueMin.poll());
            }

            // 无项目可做
            if(profitsQueueMax.isEmpty()) {
                break;
            }

            // 最大收益的项目就是第一个大根堆弹出的项目
            w += profitsQueueMax.poll().c;
        }
        return w;
    }


    public static class Program {
        public int p;
        public int c;

        public Program(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }

}
