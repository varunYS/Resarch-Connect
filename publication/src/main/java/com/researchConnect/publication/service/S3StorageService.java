package com.researchConnect.publication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.endpoint-override:#{null}}")
    private String endpointOverride;

    public String uploadFile(MultipartFile file) {
        // Generate a globally unique filename to avoid collision issues in S3
        String fileKey = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .contentType(file.getContentType())
                    .build();

            log.info("Streaming binary bits to S3 bucket '{}' with key '{}'", bucketName, fileKey);

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Construct the resource locator URL string
            return determineUrl(fileKey);

        } catch (IOException e) {
            log.error("Failed to read input stream from multipart file allocation", e);
            throw new RuntimeException("Could not read file data for S3 streaming");
        }
    }

    private String determineUrl(String fileKey) {
        // If using LocalStack, generate a local loopback link; otherwise use standard AWS patterns
        if (endpointOverride != null && !endpointOverride.isBlank()) {
            return endpointOverride + "/" + bucketName + "/" + fileKey;
        }
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileKey);
    }
}
