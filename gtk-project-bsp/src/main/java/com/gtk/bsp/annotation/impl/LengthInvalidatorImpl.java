package com.gtk.bsp.annotation.impl;

import com.gtk.bsp.annotation.MaxLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 最大长度校验实现类
 */
public class LengthInvalidatorImpl implements ConstraintValidator<MaxLength, String> {

    private MaxLength maxLength;

    /**
     * 初始化数据
     * @param constraintAnnotation 注解类
     */
    @Override
    public void initialize(MaxLength constraintAnnotation) {
        maxLength = constraintAnnotation;
    }

    /**
     * 校验是否有效
     *
     * @param fieldValue 字段值
     * @param context 限制校验上下文
     * @return 是否有效
     */
    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext context) {

        if (fieldValue.length() > maxLength.maxLength()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(maxLength.message()).addConstraintViolation();
            // 校验失败返回false。返回true上游收集不到错误信息。
            return false;
        }
        return false;
    }
}
