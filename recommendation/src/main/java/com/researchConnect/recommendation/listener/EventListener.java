package com.researchConnect.recommendation.listener;

import com.researchConnect.recommendation.dto.PublicationCreatedDto;
import com.researchConnect.recommendation.dto.UserCreatedDto;
import com.researchConnect.recommendation.service.PublicationService;
import com.researchConnect.recommendation.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EventListener {
    private final UserService userService;
    private final PublicationService publicationService;

    @KafkaListener(topics = "user.created")
    public void userCreatedEventListener(UserCreatedDto userCreatedDto){
        userService.consumeUserCreatedEvent(userCreatedDto);
    }

    @KafkaListener(topics = "publication.created")
    public void publicationCreatedListener(PublicationCreatedDto publicationCreatedDto){
        publicationService.consumePublicationCreatedEvent(publicationCreatedDto);
    }
}
