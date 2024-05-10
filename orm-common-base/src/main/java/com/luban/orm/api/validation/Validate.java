package com.luban.orm.api.validation;

import com.luban.orm.api.OrmOperation;
import com.luban.orm.exception.ValidationException;
import com.luban.orm.model.ValidateResult;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
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
