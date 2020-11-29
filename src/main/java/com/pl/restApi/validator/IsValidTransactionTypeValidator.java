package com.pl.restApi.validator;

import com.pl.restApi.model.enums.TransactionType;
import com.pl.restApi.validator.annotation.IsValidTransactionType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IsValidTransactionTypeValidator implements ConstraintValidator<IsValidTransactionType, String> {

    @Override
    public boolean isValid(String typeTransaction, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(TransactionType.values())
                .map(TransactionType::name)
                .anyMatch(type -> type.equalsIgnoreCase(typeTransaction));
    }
}
