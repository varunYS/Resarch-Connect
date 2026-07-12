package com.researchConnect.publication.service;

import com.researchConnect.publication.dto.CreatePublicationDto;
import com.researchConnect.publication.dto.UserCreatedDto;
import com.researchConnect.publication.event.PublicationCreatedEvent;
import com.researchConnect.publication.model.Publication;
import com.researchConnect.publication.model.User;
import com.researchConnect.publication.producer.PublicationEventProducer;
import com.researchConnect.publication.repository.PublicationRepository;
import com.researchConnect.publication.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicationService {
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final PublicationEventProducer publicationEventProducer;

    public void consumeUserCreatedEvent(UserCreatedDto userCreatedDto){
        User user = new User();

        user.setId(userCreatedDto.userId());
        user.setName(userCreatedDto.name());
        user.setEmail(userCreatedDto.email());
        user.setInstitution(userCreatedDto.institution());
        user.setDomains(userCreatedDto.domains());

        userRepository.save(user);
    }

    public List<User> getConsumedUser(){
        List<User> all = userRepository.findAll();
        return all;
    }

    public List<Publication> getUserPublications(Long id){
        return publicationRepository.findByAuthorId(id);
    }

    public Publication createPublication(CreatePublicationDto createPublicationDto, String s3key){
        Long authenticatedUserID = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findById(authenticatedUserID)
                .orElseThrow(() -> new IllegalStateException(
                        "User " + authenticatedUserID + " not yet synced to Publication Service"));


        Publication publication = new Publication();
        publication.setTitle(createPublicationDto.title());
        publication.setDescription(createPublicationDto.description());
        publication.setS3Key(s3key);
        publication.setAuthor(user);

        Publication savedPublication = publicationRepository.save(publication);

        PublicationCreatedEvent publicationCreatedEvent = new PublicationCreatedEvent(
                savedPublication.getId(),
                savedPublication.getTitle(),
                savedPublication.getDescription(),
                savedPublication.getS3Key(),
                authenticatedUserID
        );

        publicationEventProducer.sendPublicationCreatedEvent(publicationCreatedEvent);

        return savedPublication;
    }
}
