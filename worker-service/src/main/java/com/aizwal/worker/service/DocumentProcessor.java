package com.aizwal.worker.service;

import com.aizwal.worker.model.DocumentMetadata;
import com.aizwal.worker.repository.MetadataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DocumentProcessor {

    @Resource
    private Storage storage;

    @Resource
    private ObjectMapper mapper;

    @Resource
    private MetadataRepository metadataRepository;

    @Value("${spring.cloud.gcp.storage.bucket-name}")
    private String bucketName;

    public void processMessage(String messageJson) {
        try {
            Map<String, String> message = mapper.readValue(
                    messageJson, new TypeReference<>() {
                    }
            );
            String filePath = message.get("filePath");
            String docId = message.get("docId");
            String fileName = message.get("fileName");
            log.info("<UNK>{}", filePath);

            // Download from GCS
            Blob blob = storage.get(BlobId.of(bucketName, filePath));
            byte[] fileContent = blob.getContent();

            //Extract metadata using Tika
            InputStream stream = new ByteArrayInputStream(fileContent);

            AutoDetectParser parser = new AutoDetectParser();
            Metadata tikaMetadata = new Metadata();
            ParseContext context = new ParseContext();

            parser.parse(stream, new BodyContentHandler(), tikaMetadata, context);

            // Convert metadata to map
            Map<String, String> extracted = new HashMap<>();
            for (String name : tikaMetadata.names()) {
                extracted.put(name, tikaMetadata.get(name));
            }

            // Save to MongoDB
            metadataRepository.save(new DocumentMetadata(docId, fileName, extracted));
            log.info("Processed document: {}, extracted metadata: {}", docId, extracted);
        } catch (Exception e) {
            log.error("Error processing document message: {}", e.getMessage(), e);
        }
    }
}
