package com.aizwal.processingtopic.service;

import com.aizwal.processingtopic.model.DocumentMetadata;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DocumentSubscriber {
    @Resource
    DocumentService fileStorageService;
    @Resource MetaDataExtractorService metadataExtractorService;

    @PubSubListener("document-processing-sub")
    public void handleMessage(String docId) {
        try {
            System.out.println("Received docId: " + docId);

            File file = fileStorageService.loadTempFile(docId);
            DocumentMetadata metadata = metadataExtractorService.extractDocumentMetadata(file, docId);

            // Print or log the metadata for now
            System.out.println("Extracted metadata: " + metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
