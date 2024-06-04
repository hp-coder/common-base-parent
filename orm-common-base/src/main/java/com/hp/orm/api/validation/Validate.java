package com.hp.orm.api.validation;

import com.hp.orm.api.OrmOperation;
import com.hp.orm.exception.ValidationException;
import com.hp.orm.model.ValidateResult;
import org.springframework.util.CollectionUtils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hp
 * @date 2022/10/18
 */
public interface Validate extends OrmOperation {

    Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    default <T> void validate(T t, Class<? extends ValidateGroup> group) {
        final Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(t, group, Default.class);
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            final List<ValidateResult> validateResults = constraintViolations.stream()
                    .map(_0 -> new ValidateResult(_0.getPropertyPath().toString(), _0.getMessage()))
                    .collect(Collectors.toList());
            throw new ValidationException(validateResults);
        }
    }
}
