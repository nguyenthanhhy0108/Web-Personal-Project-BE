package com.wjh.validator;

import com.wjh.validator.constraint.PasswordConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    private int minLength;
    private int maxLength;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(password))
            return true;
        return password.length() >= minLength && password.length() <= maxLength;
    }

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.minLength = constraintAnnotation.min();
        this.maxLength = constraintAnnotation.max();
    }
}
