package com.gqs.algorithm.class014_dp;

public class T021_NQueens {

    public static int num(int n) {
        if(n < 1) {
            return 0;
        }

        int[] record = new int[n];
        return process(record, 0, n);
    }

    /**
     * 获取当前位置后面放位置的所有情况数
     *
     * @param record  存储所有行皇后位置（一维数组存储列，下标代表行）
     * @param index 在index行放皇后，所有列都尝试
     * @param n 放后数量
     * @return 当前下标后面放位置的所有情况数
     */
    private static int process(int[] record, int index,  int n) {
        // 能到达最后位置，则发现一种有效方法
        if(index == n) {
            return 1;
        }
        int res = 0;
        // 从左往右尝试所有的列
        for (int i = 0; i < n; i++) {
            // 判断符合规则，则进行下一行处理
            if(isValid(record, index, i)) {
                // 记录当前位置的列
                record[index] = i;
                // 当前行选中后，获取后面下一行的所有情况数进行累加
                res += process(record, index + 1, n);
            }
        }
        return res;
    }
    // 校验是否符合规则
    private static boolean isValid(int[] record, int index, int i) {
        for (int j = 0; j < index; j++) {
            if(i == record[j] || Math.abs(record[j] - i) == Math.abs(index - j)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 15;

        long start = System.currentTimeMillis();
        System.out.println(num(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

     /*   start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
*/
    }

}
