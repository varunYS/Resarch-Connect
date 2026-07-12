package com.researchConnect.recommendation.service;

import com.researchConnect.recommendation.dto.PublicationCreatedDto;
import com.researchConnect.recommendation.model.Publication;
import com.researchConnect.recommendation.repository.PublicationRepository;
import com.researchConnect.recommendation.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;

    public void consumePublicationCreatedEvent(PublicationCreatedDto publicationCreatedDto){
        Publication publication = new Publication();
        publication.setId(publicationCreatedDto.id());
        publication.setTitle(publicationCreatedDto.title());
        publication.setS3Key(publicationCreatedDto.s3Key());
        publication.setAuthorId(publicationCreatedDto.authorId());

        publicationRepository.save(publication);
    }
}
