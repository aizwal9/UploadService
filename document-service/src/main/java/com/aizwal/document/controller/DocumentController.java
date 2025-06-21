package com.aizwal.document.controller;

import com.aizwal.document.model.DocumentMetadata;
import com.aizwal.document.service.CloudStorageService;
import com.aizwal.document.service.DocumentPublisher;
import com.aizwal.document.service.DocumentService;
import com.aizwal.document.service.MetaDataExtractorService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {


    @Resource
    DocumentService documentService;
    @Resource
    MetaDataExtractorService metadataExtractorService;
    @Resource
    DocumentPublisher documentPublisher;
    @Resource
    CloudStorageService cloudStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }
        try {
            String docId = documentService.storeTempFile(file);
            cloudStorageService.uploadFile(file, docId + "-" + file.getOriginalFilename());
            documentPublisher.publishDocId(docId);
            return ResponseEntity.accepted().body("File received. Document ID: " + docId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    @GetMapping("/extract")
    public ResponseEntity<?> extractMetadata(@RequestParam String docId) {
        try {
            File file = documentService.loadTempFile(docId);
            DocumentMetadata metadata = metadataExtractorService.extractDocumentMetadata(file, docId);
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Extraction failed");
        }
    }

}
