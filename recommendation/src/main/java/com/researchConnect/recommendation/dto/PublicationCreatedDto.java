package com.researchConnect.recommendation.dto;

public record PublicationCreatedDto (
        Long id,
        String title,
        String description,
        String s3Key,
        Long authorId
) {}