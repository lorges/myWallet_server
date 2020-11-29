package com.pl.restApi.util.xml.validators;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class XmlValidationError {

    private int col;
    private int row;
    private String message;

    public XmlValidationError(String message) {
        this.message = message;
    }
}
