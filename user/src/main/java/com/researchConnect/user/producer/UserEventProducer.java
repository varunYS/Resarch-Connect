package com.researchConnect.user.producer;

import com.researchConnect.user.event.UserCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserCreatedEvent(UserCreatedEvent userCreatedEvent){
        kafkaTemplate.send("user.created", String.valueOf(userCreatedEvent.userId()), userCreatedEvent);
    }
}
