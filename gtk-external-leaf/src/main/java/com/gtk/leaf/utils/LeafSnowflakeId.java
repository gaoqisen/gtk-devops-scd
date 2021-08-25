package com.gtk.leaf.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class LeafSnowflakeId {

    private static final String LEAF_HOST = "http://localhost:8080/api/snowflake/get/id";
    /**
     * 生成 ID
     *
     * @return {@code Long} 雪花 ID
     */
    public static Long genId() {
        try {
            String string = Objects.requireNonNull(OkHttpClientUtils.getInstance().getData(LEAF_HOST).body()).string();
            return Long.valueOf(string);
        } catch (IOException e) {
            return 0L;
        }
    }
    /**
     * 测试
     *
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        for (int i = 0; i < 100; i++) {
            System.out.println(LeafSnowflakeId.genId());
        }
    }

}
