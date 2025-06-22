package com.aizwal.document.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudStorageService {

    @Resource
    Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket-name}")
    private String bucketName;

    public void uploadFile(MultipartFile file, String objectName) {
        try {
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());
            System.out.println("File uploaded to GCS: " + objectName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to GCS", e);
        }
    }
}
