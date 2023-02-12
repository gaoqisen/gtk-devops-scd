package com.gqs.algorithm.class010;

/**
 * 题目：给定一个字符串str，只由‘X’和‘.’两种字符构成。‘X’表示墙，不能放灯，也不需要点亮‘.’表示居民点，可以放灯，
 * 需要点亮如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。
 */
public class T005_LightAndStreets {

    /**
     * 1、遍历字符串，如果当前字符是墙（x）
     *
     * - 则跳转到i+1位置
     *
     * 2、遍历字符串，如果当前字符是街道（.）
     *
     * - 如果i+1位置是墙（x），则i位置必须放灯。如果不放灯i后面是墙那么当前位置就无法照亮（灯+1，i+2）
     * - 如果i+1位置是街道（.）,在进行细分，i+2是墙（x），则i+1或者i位置上必须放灯（灯+1， i+3）
     * - 如果i+1位置是街道（.）,在进行细分，i+2也是墙街道（.），则在i+1上面放灯，i和i+2都照亮了（灯+1，i+3）
     *
     */
    public static int getMinLight(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }

        char[] chars = str.toCharArray();
        int light = 0;
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] == '.')  {
                light++;
                if(i + 1 == chars.length) {
                    break;
                }
                if (chars[i + 1] == 'X') {
                    i += 1;
                } else {
                    i += 2;
                }
            }
        }
        return light;
    }

    // 更简洁的解法
    // 两个X之间，数一下.的数量，然后除以3，向上取整
    // 把灯数累加
    public static int minLight3(String road) {
        char[] str = road.toCharArray();
        int cur = 0;
        int light = 0;
        for (char c : str) {
            if (c == 'X') {
                light += (cur + 2) / 3;
                cur = 0;
            } else {
                cur++;
            }
        }
        light += (cur + 2) / 3;
        return light;
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = getMinLight(test);
            int ans3 = minLight3(test);
            if (ans1 != ans3) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }

}
