package com.pl.restApi.validator.annotation;

import com.pl.restApi.validator.IsValidTransactionTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { IsValidTransactionTypeValidator.class })
public @interface IsValidTransactionType {
    String message() default "Transaction type is not correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
