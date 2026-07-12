package com.researchConnect.recommendation.service;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentService {

    private final Tika tika;

    // Spring automatically injects the Tika bean configured above
    public DocumentService(Tika tika) {
        this.tika = tika;
    }

    /**
     * Detects the media type of a file (e.g., "application/pdf")
     */
    public String detectFileType(MultipartFile file) throws IOException {
        return tika.detect(file.getInputStream());
    }

    /**
     * Extracts text content from a given file
     */
    public String extractText(MultipartFile file) throws IOException {
        try {
            return tika.parseToString(file.getInputStream());
        } catch (Exception e) {
            throw new IOException("Failed to parse document text", e);
        }
    }
}
