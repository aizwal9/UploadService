package com.aizwal.processingtopic.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentMetadata {
    private String docId;
    private String title;
    private String author;
    private String contentType;
    private int pageCount;
    private long fileSize;
    private Instant uploadTime;
}