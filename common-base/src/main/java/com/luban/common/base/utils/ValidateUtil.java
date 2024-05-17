package com.luban.common.base.utils;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;


/**
 * @author hp 2022/11/8
 */
public final class ValidateUtil {
    private static final Validator VALIDATOR;

    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        return VALIDATOR.validate(object);
    }

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }
}
