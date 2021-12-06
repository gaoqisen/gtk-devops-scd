package com.gtk.common.utils;

import java.io.*;

public class SerializeUtils {

    public static final String CHARSET_NAME = "ISO-8859-1";

    /**
     * 对象转字符串
     */
    public static String object2Str(Object obj) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);

        String objectStr = byteArrayOutputStream.toString(CHARSET_NAME);
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return objectStr;
    }

    /**
     * 字符串转对象
     */
    public static Object str2Object(String str) throws Exception {
        ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(str.getBytes(CHARSET_NAME));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream);
        Object object = objectInputStream.readObject();

        byteArrayOutputStream.close();
        objectInputStream.close();
        return object;
    }

}
