package com.wjh.validator;

import com.wjh.validator.constraint.UsernameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    private int minLength;
    private int maxLength;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(username))
            return true;
        return username.length() >= minLength && username.length() <= maxLength;
    }

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.minLength = constraintAnnotation.min();
        this.maxLength = constraintAnnotation.max();
    }
}
