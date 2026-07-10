package com.researchConnect.publication.producer;

import com.researchConnect.publication.event.PublicationCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PublicationEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPublicationCreatedEvent(PublicationCreatedEvent publicationCreatedEvent){
        log.info("Publication Created Event published");
        kafkaTemplate.send("publication.created", String.valueOf(publicationCreatedEvent.id()), publicationCreatedEvent);
    }
}
