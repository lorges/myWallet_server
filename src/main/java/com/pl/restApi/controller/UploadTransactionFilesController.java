package com.pl.restApi.controller;

import com.pl.restApi.dto.UploadFilesData;
import com.pl.restApi.service.IParseFileService;
import com.pl.restApi.service.ParseFileResult;
import com.pl.restApi.util.xml.TransactionsXmlWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Log4j2
@RestController
@RequestMapping(path = "/transactions/upload")
@AllArgsConstructor
public class UploadTransactionFilesController {

    private final IParseFileService parseFileService;

    @PostMapping
    public ResponseEntity<?> uploadFiles(@ModelAttribute("form") UploadFilesData uploadFilesData) {
        log.info("UploadTransactionFilesController::uploadFiles(?) - starting uploading file with name: {} ", uploadFilesData.getFile().getOriginalFilename());
        ParseFileResult parseFileResult = parseFileService.parseFile(uploadFilesData.getFile());
        if(!parseFileResult.getErrorList().isEmpty()) {
            return ResponseEntity.badRequest().body(parseFileResult.getErrorList());
        }

        /*

             try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(TransactionsXmlWrapper.class);
            SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
            Schema schema = sf.newSchema(new File("C:\\Projekty\\edu.xsd"));

            ClassPathResource classPathResource = new ClassPathResource("static/edu.xsd");

            InputStream inputStream = classPathResource.getInputStream();
            File somethingFile = File.createTempFile("test", ".xsd");
            try {
                FileUtils.copyInputStreamToFile(inputStream, somethingFile);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Validator validator = schema.newValidator();
            List<SAXParseException> exceptions = new LinkedList<SAXParseException>();
            validator.setErrorHandler(new ErrorHandler()
            {
                @Override
                public void warning(SAXParseException exception) throws SAXException
                {
                    exceptions.add(exception);
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException
                {
                    exceptions.add(exception);
                }

                @Override
                public void error(SAXParseException exception) throws SAXException
                {
                    exceptions.add(exception);
                }
            });
            jaxbUnmarshaller.setSchema(schema);
            // specify the location and name of xml file to be read
            File XMLfile = new File("C:\\Projekty\\text.xml");
            Source xmlFile = new StreamSource(XMLfile);
            validator.validate(xmlFile);
            System.out.println(exceptions);

        } catch (JAXBException | SAXException | IOException e) {
            // some exception occured
            System.out.println(e.getCause());
            /*e.printStackTrace();
     }

         */
        return ResponseEntity.ok().build();
    }
}
