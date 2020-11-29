package com.pl.restApi.validator;


import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.validator.annotation.IsValidTransactionKind;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IsValidTransactionKindValidator implements ConstraintValidator<IsValidTransactionKind, String> {

    @Override
    public boolean isValid(String stringKind, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(TransactionKind.values())
                .map(TransactionKind::name)
                .anyMatch(kind -> kind.equalsIgnoreCase(stringKind));
    }
}
