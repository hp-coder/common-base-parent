package com.luban.orm.exception;

import com.luban.orm.model.ValidateResult;
import lombok.Getter;

import java.io.Serial;
import java.util.List;

/**
 * @author hp
 */
@Getter
public class ValidationException extends javax.validation.ValidationException {

    @Serial
    private static final long serialVersionUID = 8312899875680936694L;

    private final List<ValidateResult> result;

    public ValidationException(List<ValidateResult> result) {
        this.result = result;
    }
}
