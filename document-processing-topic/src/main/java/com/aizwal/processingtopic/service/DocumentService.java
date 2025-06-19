package com.aizwal.processingtopic.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Service
public class DocumentService {

    private static final String TEMP_DIR = "tmp/uploads/";

    public File loadTempFile(String docId) throws IOException {
        File dir = new File(TEMP_DIR);
        return Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(f -> f.getName().startsWith(docId))
                .findFirst()
                .orElseThrow(() -> new FileNotFoundException("File not found for ID: " + docId));

    }
}
