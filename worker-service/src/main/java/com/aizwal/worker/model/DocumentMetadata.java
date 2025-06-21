package com.aizwal.worker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "document_metadata")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMetadata {

    @Id
    private String docId;
    private String fileName;
    private Map<String, String> metadata;
}
