package com.gqs.algorithm.class017_FibonacciSkill;

public class T002_ZeroLeftOneStringNumber {

    public static int getNumber(int n) {
        if(n < 1) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }

        int[][] base = {
                {1, 1},
                {1, 0}
        };
        // 计算矩阵
        int[][] res = matrixPower(base, n - 2);
        /**

         Fn = (Fn-1) + (Fn-2)
         |Fn,Fn-1| = |F2,F1| * {2*2}^n-2
         则：Fn = |2,1| * {2*2}^n-2
               = |2,1| * {a,b}
                         {c,d}
               = 2a + 1c
         */
        return 2 * res[0][0] + res[1][0];
    }

    /**
     * 求出矩阵m的p次方
     */
    private static int[][] matrixPower(int[][] m, int p) {
        // 对角线的值为1，其他的值为0
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // 矩阵的1次方
        int[][] t = m;
        // 二进制右移
        for (;p != 0; p >>= 1) {
            // 判断二进制末尾是否为1
            if((p & 1) != 0) {
                res = muliMatrix(res, t);
            }
            // 每次和自己相乘2^ 4^ 8^ ...
            t = muliMatrix(t, t);
        }
        return res;
    }

    /**
     * 两个矩阵相乘
     */
    private static int[][] muliMatrix(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length; // a的列数同时也是b的行数
        int[][] ans = new int[n][m];
        for(int i = 0 ; i < n; i++) {
            for(int j = 0 ; j < m;j++) {
                for(int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }



}
