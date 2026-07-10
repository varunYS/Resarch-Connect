package com.researchConnect.publication.event;

public record PublicationCreatedEvent (
        Long id,
        String title,
        String description,
        String s3Url,
        Long authorId
) {}
