package com.researchConnect.publication.listener;

import com.researchConnect.publication.dto.UserCreatedDto;
import com.researchConnect.publication.service.PublicationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final PublicationService publicationService;

    @KafkaListener(topics = "user.created", groupId = "${spring.kafka.consumer.group-id}")
    public void userCreatedEventListener(UserCreatedDto userCreatedDto){
        log.info("User Created Event consumed by Publication service");
        publicationService.consumeUserCreatedEvent(userCreatedDto);
    }
}
