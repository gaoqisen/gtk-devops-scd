package com.gtk.common.utils;

public class RedisKeySupport {



    public static String generateCommonKey(String key) {
        return key;
    }

    public static String generateCommonKey(String key, String value) {
        return key + value;
    }

}
