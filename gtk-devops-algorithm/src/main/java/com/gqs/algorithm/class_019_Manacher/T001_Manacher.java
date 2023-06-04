package com.gqs.algorithm.class_019_Manacher;

public class T001_Manacher {

    /**
     * 获取最大回文子串长度
     *
     * @param s 字符串
     * @return 最大回文子串
     */
    public static int manacher(String s) {
        if(s == null || s.isEmpty()) {
            return 0;
        }

        // 1、将字符串转化为用特殊字符隔开，任何字符都可以，在处理字符的时候不会处理到特殊字符
        char[] str = getChars(s);

        // 回文半径数组（回文半径中的最大值/2就是返回结果）
        int[] pArr = new int[str.length];
        // 当前最右回文右边界R的回文中心点
        int C = -1;
        // 最右回文右边界(R)：只要取得了最大的右边界则R往最右边移动
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {
            /*
            R > i: i在R内，则获取最小的不用验证的区域。  否则第二种情况。 否则i在R外的最少回文半径是1
            2 * C - i: i对称值的回文半径
            R -i： 当前值(i)的对称值在L~R外， 则当前值(i)的回文半径就是i～R的长度
             */
            pArr[i] = R > i ? Math.min(pArr[2 * C -  i], R -i) : 1;
            // 对比后面的值(当前i的回文数据左右两边都存在值时)
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                // 如果左边和右边的值相等，则回文半径往后扩展，否则结束扩展
                if(str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            // R的值往后扩
            if(i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            // 获取最大的回文半径
            max = Math.max(max, pArr[i]);
        }
        // 回文半径-1就是最大回文长度(字符用特殊字符处理过)
        return max -1;
    }

    private static char[] getChars(String str) {
        char[] mStr = new char[str.length() * 2 + 1];
        char[] chars = str.toCharArray();
        int index = 0;
        for (int i = 0; i != mStr.length; i++) {
            // 奇数&1为1，偶数&1为0
            mStr[i] = (i & 1) == 0 ? '#' : chars[index++];
        }
        return mStr;
    }



    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = getChars(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
