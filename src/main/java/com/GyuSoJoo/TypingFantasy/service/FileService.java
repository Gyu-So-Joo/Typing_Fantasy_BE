package com.GyuSoJoo.TypingFantasy.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String savedFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3.putObject(bucket, savedFilename, file.getInputStream(), metadata);

        return amazonS3.getUrl(bucket, savedFilename).toString();
    }
}
