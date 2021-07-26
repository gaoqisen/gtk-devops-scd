package com.gtk.bsp.utils;

import com.gtk.bsp.entity.SysCodeMenu;
import com.gtk.bsp.exception.AppException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * validation校验工具
 */
public class ValidationUtils {

    private static Validator validation = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验是比
     * @param obj
     */
    public static void validationEntity(Object obj) {
        Set<ConstraintViolation<Object>> constraintViolations = validation.validate(obj);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            throw new AppException(message);
        }
    }

    public static void main(String[] args){
        SysCodeMenu sysCodeMenu = new SysCodeMenu();
        sysCodeMenu.setSysId("123456");
        validationEntity(sysCodeMenu);
    }

}
