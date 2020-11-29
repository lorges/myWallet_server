package com.pl.restApi.service;

import com.pl.restApi.util.xml.BaseSchemaValidator;
import com.pl.restApi.util.xml.TransactionXmlObject;
import com.pl.restApi.util.xml.TransactionsXmlWrapper;
import com.pl.restApi.util.xml.validators.XmlValidationError;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class XmlFileParserService implements IParseFileService {

    private final BaseSchemaValidator baseSchemaValidator;

    @Override
    public ParseFileResult parseFile(MultipartFile multipartFile) {
        log.info("XmlFileParserService:: parseFile(?) - file " + multipartFile.getOriginalFilename());
        List<XmlValidationError> xmlValidationErrorList;
        ParseFileResult parseFileResult = new ParseFileResult();

        try {
            File XMLfile = File.createTempFile("test", ".xml");
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), XMLfile);

            JAXBContext jaxbContext = JAXBContext.newInstance(TransactionsXmlWrapper.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TransactionsXmlWrapper transactionsWrapper = (TransactionsXmlWrapper) jaxbUnmarshaller.unmarshal(XMLfile);

            xmlValidationErrorList = baseSchemaValidator.schemaValidate(XMLfile,"static/edu.xsd");
            if(null != xmlValidationErrorList && !xmlValidationErrorList.isEmpty()) {
                parseFileResult.setErrorList(xmlValidationErrorList);
                return parseFileResult;
            } else {
                for(TransactionXmlObject transaction : transactionsWrapper.getTransactionList()) {
                    if(StringUtils.isBlank(transaction.getTransactionName())) {
                        xmlValidationErrorList.add(new XmlValidationError(""));
                    }
                    if(StringUtils.isBlank(transaction.getTransactionDesc())) {
                        xmlValidationErrorList.add(new XmlValidationError(""));
                    }
                    if(StringUtils.isBlank(transaction.getTransactionKind())) {
                        xmlValidationErrorList.add(new XmlValidationError(""));
                    }
                    if(StringUtils.isBlank(transaction.getTransactionType())) {
                        xmlValidationErrorList.add(new XmlValidationError(""));
                    }
                    if(null == transaction.getTransactionAmount()) {
                        xmlValidationErrorList.add(new XmlValidationError(""));
                    }
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseFileResult;
    }
}
