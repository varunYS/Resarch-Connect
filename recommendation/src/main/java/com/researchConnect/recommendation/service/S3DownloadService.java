package com.researchConnect.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class S3DownloadService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public byte[] downloadFile(String s3Key) throws IOException {
        return s3Client.getObject(
                GetObjectRequest.builder().bucket(bucketName).key(s3Key).build()
        ).readAllBytes();
    }
}
