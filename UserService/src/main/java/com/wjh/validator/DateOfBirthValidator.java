package com.wjh.validator;

import com.wjh.validator.constraint.DateOfBirthConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirthConstraint, LocalDate> {

    private int min = 0;

    @Override
    public void initialize(DateOfBirthConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= this.min;
    }
}
