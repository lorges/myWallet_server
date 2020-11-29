package com.pl.restApi.validator.annotation;

import com.pl.restApi.validator.IsValidTransactionKindValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { IsValidTransactionKindValidator.class })
public @interface IsValidTransactionKind {
    String message() default "Transaction kind is not correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
