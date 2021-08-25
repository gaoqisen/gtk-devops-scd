

package com.gtk.bsp.utils;


import com.gtk.bsp.exception.AppException;
import org.springframework.util.StringUtils;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new AppException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new AppException(message);
        }
    }
}
