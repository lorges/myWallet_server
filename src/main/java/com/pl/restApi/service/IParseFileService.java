package com.pl.restApi.service;

import org.springframework.web.multipart.MultipartFile;

public interface IParseFileService {

    ParseFileResult parseFile(MultipartFile multipartFile);
}
