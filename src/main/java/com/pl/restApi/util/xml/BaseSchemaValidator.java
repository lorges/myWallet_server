package com.pl.restApi.util.xml;

import com.pl.restApi.util.xml.validators.ValidateErrorHandler;
import com.pl.restApi.util.xml.validators.XmlValidationError;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Log4j2
@Service
public class BaseSchemaValidator {

    public List<XmlValidationError> schemaValidate(File fileToValidation, String xsdSchemaPath) {
        log.info("BaseSchemaValidator:: schemaValidate(?,?) - file " + fileToValidation.getName());
        List<XmlValidationError> validationErrors = new ArrayList<>();
        try {
            Schema schema = getSchema(xsdSchemaPath);
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new ValidateErrorHandler(validationErrors));

            Source xmlFile = new StreamSource(fileToValidation);
            validator.validate(xmlFile);
        } catch (SAXException e) {
            log.error("BaseSchemaValidator:: schemaValidate(?,?) - validation xsd exception");
        } catch (IOException e) {
            log.error("BaseSchemaValidator:: schemaValidate(?,?) - problem with xsdSchema file");
        }
        return validationErrors;
    }

    private Schema getSchema(String schemaPath) throws SAXException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return sf.newSchema(getFileFromResource(schemaPath));
    }

    private File getFileFromResource(String schemaPath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(schemaPath);
        File resourceFile = File.createTempFile("test", ".xsd");
        try(InputStream inputStream = classPathResource.getInputStream()) {
            FileUtils.copyInputStreamToFile(inputStream, resourceFile);
        }
        return resourceFile;
    }
}
