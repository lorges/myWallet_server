package com.pl.restApi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFilesData {
    private MultipartFile file;
}

