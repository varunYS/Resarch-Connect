package com.researchConnect.recommendation.service;

import com.researchConnect.recommendation.dto.UserCreatedDto;
import com.researchConnect.recommendation.model.User;
import com.researchConnect.recommendation.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void consumeUserCreatedEvent(UserCreatedDto userCreatedDto){
        User user = new User();
        user.setId(userCreatedDto.userId());
        user.setName(userCreatedDto.name());
        user.setEmail(userCreatedDto.email());
        user.setDomains(userCreatedDto.domains());

        userRepository.save(user);
    }


}
