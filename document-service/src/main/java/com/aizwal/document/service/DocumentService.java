package com.aizwal.document.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
public class DocumentService {

    private static final String TEMP_DIR = "tmp/uploads/";

    public String storeTempFile(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(TEMP_DIR));
        String docId = UUID.randomUUID().toString();
        Path filePath = Paths.get(TEMP_DIR + docId + "-" + file.getOriginalFilename());
        file.transferTo(filePath);
        return docId;
    }

    public File loadTempFile(String docId) throws IOException {
        File dir = new File(TEMP_DIR);
        return Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(f -> f.getName().startsWith(docId))
                .findFirst()
                .orElseThrow(() -> new FileNotFoundException("File not found for ID: " + docId));

    }
}
