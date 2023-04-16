package com.gqs.algorithm.class014_dp;

import java.util.HashMap;

// 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
public class T007_StickersToSpellWord {

    /**
     * 获取最小贴纸
     *
     * @param stickers 所有的贴纸
     * @param target 最终需要剪的值
     * @return 最小贴纸数量
     */
    public static int minStickers(String[] stickers, String target) {
        if(stickers == null || stickers.length < 1 || target == null || target.isEmpty()) {
            return 0;
        }
        int ans = process(stickers, target);
        return Integer.MAX_VALUE == ans ? -1 : ans;
    }

    /**
     * 递归获取贴纸数量
     *
     * @param stickers 所有的贴纸
     * @param target 最终需要剪的值
     * @return 最小贴纸数量
     */
    private static int process(String[] stickers, String target) {
        // 没有需要拼接的贴纸，直接返回0
        if(target.isEmpty()) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String sticker : stickers) {
            String rest = minus(target, sticker);
            // 当前贴纸没有一个符合
            if(rest.length() == target.length()) {
                continue;
            }
            // 递归获取剩余字符的最小贴纸数量
            min = Math.min(min, process(stickers, rest));
        }
        // 如果没有贴纸符合则返回最大integer， 否则最小贴纸数量加上当前字符数量
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 减去贴纸后获取剩余的字符
     *
     * @param target 需要的字符
     * @param sticker 贴纸上面的字符
     * @return 减去后剩余的字符
     */
    private static String minus(String target, String sticker) {
        char[] targets = target.toCharArray();
        char[] stickers = sticker.toCharArray();
        int[] counts = new int[26];
        // 将字符的数量放入数组中
        for (char c : targets) {
            counts[c-'a']++;
        }
        // 减去贴纸的字符数量
        for (char c : stickers) {
            counts[c-'a']--;
        }

        // 将剩余的字符组装为字符串
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < counts.length; i++) {
            if(counts[i] > 0) {
                for (int j = 0; j < counts[i]; j++) {
                    builder.append((char)(i + 'a'));
                }
            }
        }
        return builder.toString();
    }


    /**
     * 递归处理贴纸（用二维数组优化版-剪枝）
     *
     * @param stickers 所有的贴纸
     * @param target 最终需要剪的值
     * @return 最小贴纸数量
     */
    public static int minStickers1(String[] stickers, String target) {
        int n = stickers.length;
        // 将贴纸转化为二维数组
        int[][] counts = new int[n][26];
        for (int i = 0; i < n; i++) {
            char[] chars = stickers[i].toCharArray();
            for (int i1 = 0; i1 < chars.length; i1++) {
                counts[i][chars[i1] - 'a'] ++;
            }
        }
        // 递归获取最小贴纸
        int ans = process1(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 贴纸优化版本
     *
     * @param stickers 二维数组处理贴纸
     * @param target 剩余需要匹配的字符
     * @return 最小贴纸数量
     */
    private static int process1(int[][] stickers, String target) {
        if(target.length() == 0) {
            return 0;
        }

        // 用一维数组转化target
        char[] chars = target.toCharArray();
        int[] targetCounts = new int[26];
        for (char aChar : chars) {
            targetCounts[aChar - 'a']++;
        }

        int n = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int[] sticker = stickers[i];
            // 剪枝，贴纸包含第一个字符才处理，减少处理次数(只需要处理一次，没必要重复跑)，贪心
            if(sticker[chars[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    // 剩余字符转化的一维数组存在字符才处理
                    if(targetCounts[j] > 0) {
                        // 剩余的字符
                        int nums = targetCounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char)(j + 'a'));
                        }
                    }
                }
                String reset = builder.toString();
                // 递归获取最小贴纸数
                min = Math.min(min, process1(stickers, reset));
            }
        }

        // 如果没有贴纸符合则返回最大integer， 否则最小贴纸数量加上当前字符数量
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }


    /**
     * 递归处理贴纸（用二维数组优化版-剪枝）+ 缓存（记忆化搜索）
     *
     * @param stickers 所有的贴纸
     * @param target 最终需要剪的值
     * @return 最小贴纸数量
     */
    public static int minStickers2(String[] stickers, String target) {
        int n = stickers.length;
        // 将贴纸转化为二维数组
        int[][] counts = new int[n][26];
        for (int i = 0; i < n; i++) {
            char[] chars = stickers[i].toCharArray();
            for (int i1 = 0; i1 < chars.length; i1++) {
                counts[i][chars[i1] - 'a'] ++;
            }
        }
        // 递归获取最小贴纸
        HashMap<String, Integer> cache = new HashMap<>();
        cache.put("", 0);
        int ans = process2(counts, target,cache);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 贴纸优化版本
     *
     * @param stickers 二维数组处理贴纸
     * @param target 剩余需要匹配的字符
     * @return 最小贴纸数量
     */
    private static int process2(int[][] stickers, String target, HashMap<String, Integer> cache) {
        if(cache.containsKey(target)) {
            return cache.get(target);
        }
        if(target.length() == 0) {
            return 0;
        }

        // 用一维数组转化target
        char[] chars = target.toCharArray();
        int[] targetCounts = new int[26];
        for (char aChar : chars) {
            targetCounts[aChar - 'a']++;
        }

        int n = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int[] sticker = stickers[i];
            // 剪枝，贴纸包含第一个字符才处理，减少处理次数
            if(sticker[chars[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    // 剩余字符转化的一维数组存在字符才处理
                    if(targetCounts[j] > 0) {
                        // 剩余的字符
                        int nums = targetCounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char)(j + 'a'));
                        }
                    }
                }
                String reset = builder.toString();
                // 递归获取最小贴纸数
                min = Math.min(min, process2(stickers, reset, cache));
            }
        }

        // 如果没有贴纸符合则返回最大integer， 否则最小贴纸数量加上当前字符数量
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        cache.put(target, ans);
        return ans;
    }




}
