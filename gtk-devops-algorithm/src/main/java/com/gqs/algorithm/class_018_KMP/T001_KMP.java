package com.gqs.algorithm.class_018_KMP;

public class T001_KMP {

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
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


    /**
     * 在a字符串中获取b字符串第一次出现的下标位置
     * @param a
     * @param b
     * @return
     */
    public static int getIndexOf(String a, String b) {
        if(a == null || b == null || b.length() < 1 || a.length() < b.length()) {
            return -1;
        }
        char[] str1 = a.toCharArray();
        char[] str2 = b.toCharArray();
        // 记录当前str1和str2比对到的位置
        int x = 0, y = 0;
        // 获取next数组
        int[] next = getNextArray(str2);
        while (x < str1.length && y < str2.length){
            // 如果字符串相等
            if(str1[x] == str2[y]) {
                x++;
                y++;
            }
            // y等于0时（在str2中只有0位置的值是-1），不能往左走了
            else if (next[y] == -1) {
                x++;
            }
            // 能往左走时，跳过不必要多余的比较
            else {
               y = next[y];
            }
        }
        // str2对比的位置越界则表示找到了匹配的字符串，否则没有
        return y == str2.length ? x - y : -1;
    }

    /**
     * 获取字符串的next数组（失配数组）
     */
    private static int[] getNextArray(char[] str) {
        if(str.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        // 初始化默认值
        next[0] = -1;
        next[1] = 0;

        // 当前求next值位置，从2开始
        int i = 2;
        // 当前位置在和i-1的位置进行比较
        int n = 0;
        while (i < str.length) {
            // 匹配上的时候
            if(str[i - 1] == str[n]) {
                /*
                 匹配上则i位置的数量+1后，后面值比较时直接使用
                 next[i] = n + 1;
                 i++;n++;
                 */
                next[i++] = ++n;
            }
            // 能往左走则一只往左走
            else if (n > 0) {
                n = next[n];
            }
            // 不能往左走了则没有匹配上
            else {
                next[i++] = 0;
            }
        }
        return next;
    }


}
