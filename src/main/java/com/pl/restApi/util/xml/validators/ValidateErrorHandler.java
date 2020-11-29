package com.pl.restApi.util.xml.validators;

import lombok.AllArgsConstructor;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ValidateErrorHandler implements ErrorHandler {

    private List<XmlValidationError> validationErrors;

    public void warning(SAXParseException ex) {
        validationErrors.add(new XmlValidationError(ex.getLineNumber(), ex.getColumnNumber(), ex.getMessage()));
    }

    public void error(SAXParseException ex) {
        validationErrors.add(new XmlValidationError(ex.getLineNumber(), ex.getColumnNumber(), ex.getMessage()));
    }

    public void fatalError(SAXParseException ex) {
        validationErrors.add(new XmlValidationError(ex.getLineNumber(), ex.getColumnNumber(), ex.getMessage()));
    }
}
