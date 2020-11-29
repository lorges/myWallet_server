package com.pl.restApi.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> handleTransactionNotFound(Exception ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                "Transaction not found"
            );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
