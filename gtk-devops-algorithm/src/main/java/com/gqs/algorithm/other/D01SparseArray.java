package com.gqs.algorithm.other;

/**
 * 稀疏数组
 */
public class D01SparseArray {

    public static void main(String[] args) {
        // 生成行列都是11位的二维数组
        int[][] array2 = new int[11][11];
        array2[2][3] = 1;
        array2[5][2] = 2;
        array2[7][7] = 2;
        println(array2);

        int[][] sparse = twoArrayToSparse(array2);
        System.out.println("二维转稀疏");
        println(sparse);
        System.out.println();

        System.out.println("稀疏转二维");
        int[][] ints = sparseToTwoArray(sparse);
        println(ints);
        System.out.println();
    }



    /**
     * 稀疏数组转二维
     */
    private static int[][] sparseToTwoArray(int[][] sparse) {
        int[][] array = new int[sparse[0][0]][sparse[0][1]];
        for (int i = 1; i <= sparse[0][2]; i++) {
            int[] row = sparse[i];
            int h = row[0];
            int l = row[1];
            array[h][l] = row[2];
        }
        return array;
    }


    /**
     * 二维数组转稀疏
     */
    private static int[][] twoArrayToSparse(int[][] array) {
        int sum = 0;
        for (int[] row : array) {
            for (int k : row) {
                if (k != 0) {
                    sum++;
                }
            }
        }

        int[][] sparse = new int[sum+1][3];
        sparse[0][0] = array.length;
        sparse[0][1] = array[0].length;
        sparse[0][2] = sum;

        int count = 0;
        for (int i = 1; i < array.length; i++) {
            int[] row = array[i];
            for (int j = 0; j < row.length; j++) {
                if(row[j] != 0) {
                    count++;
                    sparse[count][0] = i;
                    sparse[count][1] = j;
                    sparse[count][2] = row[j];
                }
            }
        }
        return sparse;
    }


    private static void println(int[][] array) {
        for(int[] arr : array) {
            for(int i : arr) {
                System.out.printf("%d\t", i);
            }
            System.out.println();
        }
    }

}
