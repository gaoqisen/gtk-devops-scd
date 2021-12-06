package com.gtk.common.annotion;

import java.lang.annotation.*;

/**
 * redis缓存
 *
 * @author gaoqisen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCache {

    /**
     * 缓存key
     */
    String key() default "";

    /**
     * 缓存时间（单位秒）
     * 默认缓存30分钟
     */
    int time() default 60 * 30;

    /**
     * 业务key
     * 例子: #statusEnum.group
     */
    String bizKey() default "";
}