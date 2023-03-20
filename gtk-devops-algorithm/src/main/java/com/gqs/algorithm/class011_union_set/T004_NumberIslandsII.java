package com.gqs.algorithm.class011_union_set;

import java.util.ArrayList;
import java.util.List;

// 获取岛数量（随机给定值并获取数量）
// 测试链接：https://leetcode.com/problems/number-of-islands-ii/
public class T004_NumberIslandsII {


    // 给定二维数组和指定的岛数量，获取每多一个值的岛数量
    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        // 先初始化数组
        UnionArray unionArray = new UnionArray(m, n);
        List<Integer> result = new ArrayList<>();
        for (int[] position : positions) {
            // 每个点进行链接合并后获取岛数量
            result.add(unionArray.connect(position[0], position[1]));
        }
        return result;
    }

    private static class UnionArray{

        private int[] parent;
        // 通过sizes[i]是否等于0标记是否初始化过
        private int[] sizes;
        private int[] help;
        private int size;
        private int row;
        private int col;

        public UnionArray(int r, int c) {
            col = c;
            row = r;
            int length = r * c;
            parent = new int[length];
            sizes = new int[length];
            help = new int[length];
            size = 0;
        }

        public int connect(int r, int c) {
            // 判断当前位置已经合并过则直接返回数量
            int index = getIndex(r, c);
            if(sizes[index] == 0) {
                // 给当前值赋值
                parent[index] = index;
                sizes[index] = 1;
                size++;
                // 当前值 的前后左右进行合并
                union(r -1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return size;
        }

        // 并查集的合并操作
        private void union(int ar, int ac, int br, int bc) {
            if(ar < 0 || ar == row || ac < 0 || ac == col || br < 0 || br == row || bc < 0 || bc == col) {
                return;
            }
            int ai = getIndex(ar, ac);
            int bi = getIndex(br, bc);
            if(sizes[ai] == 0 || sizes[bi] == 0) {
                return;
            }
            int af = findAncestor(ai);
            int bf = findAncestor(bi);
            if(af != bf) {
                int aLength = sizes[af];
                int bLength = sizes[bf];
                if(aLength > bLength) {
                    parent[bf] = af;
                    sizes[af] += sizes[bf];
                } else {
                    parent[af] = bf;
                    sizes[bf] += sizes[af];
                }
                size--;
            }
        }

        // 获取下标
        private int getIndex(int r, int c) {
            return r * col + c;
        }

        // 获取祖先节点
        private int findAncestor(int val) {
            int i = 0;
            while (val != parent[val]) {
                help[i++] = val;
                val = parent[val];
            }
            for(i--; i>=0; i--) {
                parent[help[i]] = val;
            }
            return val;
        }
    }

}
