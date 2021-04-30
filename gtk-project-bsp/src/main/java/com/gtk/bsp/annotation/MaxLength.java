package com.gtk.bsp.annotation;

import com.gtk.bsp.annotation.impl.LengthInvalidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LengthInvalidatorImpl.class)
@Documented
public @interface MaxLength {

    // 字段支持的最大长度(字符数)
    int maxLength() default 5;

    // 校验失败后返回的错误信息
    String message() default "超过最大长度";

    // 分组
    Class<?>[] groups() default {};

    // 负载
    Class<? extends Payload>[] payload() default {};

}
