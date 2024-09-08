package com.wjh.validator.constraint;

import com.wjh.validator.DateOfBirthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {DateOfBirthValidator.class}
)
public @interface DateOfBirthConstraint {
    String message() default "INVALID_DOB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;
}
