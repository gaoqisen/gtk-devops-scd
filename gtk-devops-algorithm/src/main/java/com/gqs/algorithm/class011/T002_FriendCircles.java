package com.gqs.algorithm.class011;

/**
 * 朋友圈问题
 * https://leetcode.com/problems/friend-circles/
 */
public class T002_FriendCircles {

    public static void main(String[] args) {

        int[][] data = new int[][]{{1,1,0},{1,1,0},{0,0,1}};
        System.out.println(findCircleNum(data));

    }


    public static int findCircleNum(int[][] M) {
        int n = M.length;
        UnionFind unionFind = new UnionFind(n);
        // 遍历数组
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                // 如果i位置和j位置相连则合并
                if(M[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }

        return unionFind.sets;
    }
    public static class UnionFind{
        /**
         * 父亲节点
         * [0,1]： 代表下标为0节点的父节点为0，下标为1节点的父节点为1
         * [1,0]： 代表下标为0节点的父节点为1，下标为1节点的父节点为0
         */
        private int[] parent;

        /**
         * 存放大小
         */
        private int[] size;

        // 辅助数组，用来代替栈
        private int[] help;

        // 集合数量
        private int sets;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            sets = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // 查找祖先
        private int find(int n) {
            int i = 0;
            // 遍历如果n的父值不是n则往上赋值， help存父值
            while (n != parent[n]) {
                help[i++] = parent[n];
                n = parent[n];
            }
            // 路径压缩，将help里面的值都指向n
            for (i--; i>=0; i--) {
                parent[help[i]] = n;
            }
            return n;
        }

        private void union(int i, int j) {
            int i1 = find(i);
            int i2 = find(j);
            // 两个父节点不是一个则合并
            if(i1 != i2) {
                // 数量小的集合合并到数量大的集合
                if(size[i1] > size[i2]) {
                    parent[i2] = i1;
                    size[i1] += size[i2];
                } else {
                    parent[i1] = i2;
                    size[i2] += size[i1];
                }
                sets--;
            }
        }
    }
}
