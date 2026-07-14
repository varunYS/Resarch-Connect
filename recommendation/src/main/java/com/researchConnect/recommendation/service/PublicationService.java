package com.researchConnect.recommendation.service;

import com.researchConnect.recommendation.dto.PublicationCreatedDto;
import com.researchConnect.recommendation.model.Publication;
import com.researchConnect.recommendation.repository.PublicationRepository;
import com.researchConnect.recommendation.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;

@Service
@AllArgsConstructor
@Slf4j
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final S3DownloadService s3DownloadService;
    private final TikaExtractionService tikaExtractionService;

    public void consumePublicationCreatedEvent(PublicationCreatedDto publicationCreatedDto){
        Publication publication = new Publication();
        publication.setId(publicationCreatedDto.id());
        publication.setTitle(publicationCreatedDto.title());
        publication.setS3Key(publicationCreatedDto.s3Key());
        publication.setAuthorId(publicationCreatedDto.authorId());

        try{
            byte[] pdfBytes = s3DownloadService.downloadFile(publicationCreatedDto.s3Key());
            String fullText = tikaExtractionService.extractText(pdfBytes);
            String abstractText = tikaExtractionService.extractAbstract(fullText);

            System.out.println(abstractText);

        } catch (Exception e) {
            log.warn("Failed to process publication", e);
            System.out.println(publicationCreatedDto.s3Key());
        }

        publicationRepository.save(publication);
    }


}
