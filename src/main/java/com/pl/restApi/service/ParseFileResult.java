package com.pl.restApi.service;

import com.pl.restApi.util.xml.validators.XmlValidationError;
import lombok.Data;

import java.util.List;

@Data
public class ParseFileResult {

    List<XmlValidationError> errorList;
}
