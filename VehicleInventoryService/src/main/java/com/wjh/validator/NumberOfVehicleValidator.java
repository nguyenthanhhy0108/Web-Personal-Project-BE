package com.wjh.validator;

import com.wjh.validator.constraint.NumberOfVehicleConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberOfVehicleValidator implements ConstraintValidator<NumberOfVehicleConstraint, Integer> {

    private int min;

    @Override
    public void initialize(NumberOfVehicleConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer >= min;
    }
}
