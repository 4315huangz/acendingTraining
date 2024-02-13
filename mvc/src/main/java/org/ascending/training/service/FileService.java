package org.ascending.training.service;

import com.amazonaws.services.s3.model.PutObjectRequest;
import org.ascending.training.service.exception.InvalidFileTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class FileService {
    private String bucketName = "ziwei-ascending-2101";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AmazonS3 s3Client;

    public void uploadFile(File file) throws IOException {
        s3Client.putObject(bucketName, file.getName(), file);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if(file == null) {
            logger.error("Cannot upload a null file");
            throw new InvalidFileTypeException("File cannot be null");
        }
        PutObjectRequest request = new PutObjectRequest(bucketName,
                file.getOriginalFilename(), file.getInputStream(), null);
        s3Client.putObject(request);
        return s3Client.getUrl(bucketName, file.getName()).toString();
    }
}
