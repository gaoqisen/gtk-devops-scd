package com.gqs.algorithm.class017_FibonacciSkill;

public class T001_FibonacciSkill {

    /**
     * 普通的递归方式计算
     */
    public static int f(int n) {
        Integer x = baseCase(n);
        if (x != null) {
            return x;
        }
        return f(n - 1) + f(n - 2);
    }

    /**
     * 通过矩阵方式
     */
    public static int f1(int n) {
        // 基础条件判断
        Integer x = baseCase(n);
        if (x != null) {
            return x;
        }
        /**
         严格递推公式: F(n) = F(n-1) + F(n-2)， 减去值最多的值是2则改递推式为二阶递推
         线性代数就是为了解决严格递推式而出现的
         则得出行列式：|F3,F2| = |F2,F1| * {a,b}
                                        {c,d}
                    |F4,F3| = |F3,F2| * {a,b}
                                        {c,d}
         通过前几个简单的计算值（1，1，2，3，5，8）计算可得出基础矩阵
         则|F3,F2|：|2,1| = |1,1| * {a,b}
                                   {c,d}
         根据矩阵乘法求出： 1*a + 1*c = 2， 1*b + 1*d = 1
         则|F4,F3|：|3,2| = |2,1| * {a,b}
                                   {c,d}
         根据矩阵乘法求出： 2*a + 1*c = 3， 2*b + 1*d = 2
         则更具两个式子得出base矩阵值如下：
         */
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        // 计算矩阵
        int[][] res = matrixPower(base, n - 2);
        // 公式：|Fn,Fn-1| = |F2,F1| * |base|^n-2
        // 替换F2和F1的值：|Fn,Fn-1| = |1,1| * |base|^n-2
        // 如果base矩阵计算得出{a,b}
        //                  {c,d}
        // 则：Fn = 1*a + 1*c = a + c
        return res[0][0] + res[1][0];
    }

    // 计算n年后牛的数量
    public static int countCowNum(int n) {
        if(n < 1) {
            return 0;
        }
        if(n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 基础矩阵，通过计算数据后推演得出
        int[][] baseCase = {
                {1,1,0},
                {0,0,1},
                {1,0,0}
        };
        int[][] res = matrixPower(baseCase, n - 3);
        // 通过行列式计算得出
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
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

    private static Integer baseCase(int n) {
        if(n < 1) {
            return 0;
        }
        if(n == 1 || n == 2) {
            return 1;
        }
        return null;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(f(48));
        System.out.println((System.currentTimeMillis() - start) + "ms");
        long start1 = System.currentTimeMillis();
        System.out.println(start1);
        System.out.println(f1(48));
        System.out.println(System.currentTimeMillis());
        System.out.println((System.currentTimeMillis() - start1) + "ms");

    }

}
