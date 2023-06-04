package com.gqs.algorithm.class016_monotonousStack;

// 测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
public class T005_CountSubmatricesWithAllOnes {

    public static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int nums = 0;
        // 定义直方图
        int[] height = new int[mat[0].length];
        // 遍历二维数组
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                // 判断当前位置是0则值为0，否则增加直方图高度
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            // 获取当前行的所有矩形数量进行累加
            nums += countFromBottom(height);
        }
        return nums;
    }

    /**
     * 获取arr直方图里面的所有矩形数量
     *
     *
     * @param height 直方图数组
     * @return 直方图里面的所有矩形数量
     */
    private static int countFromBottom(int[] height) {
        if(height == null || height.length < 1) {
            return 0;
        }
        int nums = 0;
        // 数组栈，替代系统栈，性能好. 记录直方图下标
        int[] stack = new int[height.length];
        // 栈里面的数量
        int si = -1;
        for (int i = 0; i < height.length; i++) {
            // 当栈不为空，并且栈里面的值大于等于当前值。则弹出栈
            while (si != -1 && height[stack[si]] >= height[i]) {
                // 弹出当前元素（当前值的左就是si，右就是i）
                int pop = stack[si--];
                // 只处理栈里面的值大于当前值的数据，相等的数据不用处理，最后一次处理即可（忽略相等）
                if(height[pop] > height[i]) {
                    // 左边的最小值就是弹出后栈里面的最大值
                    int left = si == -1 ? -1 : stack[si];
                    // 需要计算矩形数据的长度
                    int n = i - left - 1;
                    // 获取左右两边临近位置相对大的值
                    int down = Math.max(left == -1 ? 0 : height[left], height[i]);
                    // 从上往下计算到当前能处理的位置（不计算后面矩形可以计算的位置）
                    int h = height[pop] - down;
                    // 累加高度乘以大区域里面的所有矩形数量
                    nums += h * num(n);
                }
            }
            // 入栈
            stack[++si] = i;
        }

        // 处理栈里面剩余的值
        while (si != -1) {
            int pop = stack[si--];
            // 左边的最小值就是弹出后栈里面的最大值
            int left = si == -1 ? -1 : stack[si];
            // 需要计算矩形数据的边长
            int n = height.length - left - 1;
            // 根据公式计算所有的矩形面积
            int down = left == -1 ? 0 : height[left];
            nums += (height[pop] - down) * num(n);
        }

        return nums;
    }

    /**
     * 关键点：通过这个公式就可以计算出一行的所有矩形的数量
     *
     * 以一行为一个大区域的有n列产生的矩形数量
     */
    public static int num(int n) {
        return ((n * (1 + n)) >> 1);
    }

}
