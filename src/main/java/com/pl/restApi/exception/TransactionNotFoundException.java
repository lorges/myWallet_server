package com.pl.restApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException() {
        super();
    }
}
