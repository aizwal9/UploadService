package com.aizwal.processingtopic.service;

import com.aizwal.processingtopic.model.DocumentMetadata;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.Instant;

@Service
public class MetaDataExtractorService {

    private final Tika tika = new Tika();

    public DocumentMetadata extractDocumentMetadata(File file, String docId) {
        try (InputStream stream = Files.newInputStream(file.toPath())) {
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            ParseContext context = new ParseContext();
            BodyContentHandler handler = new BodyContentHandler(-1); // unlimited

            parser.parse(stream, handler, metadata, context);

            return DocumentMetadata.builder()
                    .docId(docId)
                    .title(metadata.get("title"))
                    .author(metadata.get("Author"))
                    .contentType(tika.detect(file))
                    .pageCount(parsePageCount(metadata))
                    .fileSize(file.length())
                    .uploadTime(Instant.now())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract metadata", e);
        }
    }

    private int parsePageCount(Metadata metadata) {
        try {
            String pageCount = metadata.get("xmpTPg:NPages"); // works for PDFs
            return pageCount != null ? Integer.parseInt(pageCount) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

}
