package com.power.power_pdf.service;

import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class StorageService {

    @Autowired
    private MinioClient minioClient;

    public void upload(String bucketName, String objectName, InputStream inputStream, String contentType ) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject (
                    PutObjectArgs.builder().bucket(bucketName).object(objectName)
                            .stream(inputStream, inputStream. available (), - 1 )
                            .contentType(contentType)
                            .build());
        } catch(Exception e) {
            log.error("Erro (StorageService.upload): ", e.getMessage());
        }
    }

    public InputStream download(String bucketName, String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch(Exception e) {
            log.error("Erro (StorageService.download): ", e.getMessage());
        }
        return null;
    }
}
