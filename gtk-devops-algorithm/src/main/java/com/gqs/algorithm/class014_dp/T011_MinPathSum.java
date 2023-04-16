package com.gqs.algorithm.class014_dp;

public class T011_MinPathSum {

    // dp动态规划+计算整张dp表
    public static int minPathSum(int[][] m) {
        if(m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        // 先填第一行
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        // 在填第一列
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + m[0][i];
        }

        // 其他数据获取左边和上面小的值
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    // dp动态规划 + 空间压缩（只计算一个数组）
    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }

        int row = m.length;
        int col = m[0].length;
        int[] arr = new int[col];
        arr[0] = m[0][0];
        // 初始化第一列
        for (int i = 1; i < col; i++) {
            arr[i] = arr[i - 1] + m[0][i];
        }
        for (int i = 1; i < row; i++) {
            // 先计算出第一行下标为0的值
            arr[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                // 当前j下标前一个就是当前值左边的值，当前位置的值就是上面的值
                // 取左边和上边最小的值 + 当前位置的值
                arr[j] = Math.min(arr[j - 1], arr[j]) + m[i][j];
            }
        }
        return arr[col - 1];
    }


    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    // for test
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum(m));
        System.out.println(minPathSum1(m));

    }

}
