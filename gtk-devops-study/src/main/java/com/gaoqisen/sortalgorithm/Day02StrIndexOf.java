package com.gaoqisen.sortalgorithm;

public class Day02StrIndexOf {

    public static void main(String[] args) {
        System.out.println(strStr("a", "a"));
    }

    /**
     * 给你两个字符串haystack 和 needle ，请你在 haystack 字符串中找出 needle
     * 字符串出现的第一个位置（下标从 0 开始）。如果不存在，则返回 -1 。
     *
     * 1. 将字符串都转为char数组
     * 2. 遍历字符串，用字符和需要对比的字符进行比较
     * 3. 如果所有的字符都对比成功则直接返回i就是第一个出现的下标
     */
    public static int strStr(String haystack, String needle) {
        char[] chars = haystack.toCharArray();
        char[] needleChars = needle.toCharArray();
        if(chars.length == 0 && needleChars.length == 0) {
            return 0;
        }
        for (int i = 0; i < chars.length - needleChars.length + 1; i++) {
            int j = 0;
            for (int k = 0; k < needleChars.length; k++) {
                if(chars[i + k] == needleChars[k]) {
                    j++;
                } else {
                    break;
                }
            }
            if(j == needleChars.length) {
                return i;
            }
        }
        return -1;
    } 

}
