package com.researchConnect.user.service;

import com.researchConnect.user.config.JwtService;
import com.researchConnect.user.dto.LoginRequestDto;
import com.researchConnect.user.dto.UserRegistrationDto;
import com.researchConnect.user.event.UserCreatedEvent;
import com.researchConnect.user.model.User;
import com.researchConnect.user.producer.UserEventProducer;
import com.researchConnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEventProducer userEventProducer;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public User userRegistrationService(UserRegistrationDto userRegistrationDto){

        System.out.println("User Register Service reached");



        User user = new User();
        user.setName(userRegistrationDto.name());
        user.setEmail(userRegistrationDto.email());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.password()));
        user.setInstitution(userRegistrationDto.institution());
        user.setDomains(userRegistrationDto.domains());

        User savedUser = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getInstitution(),
                savedUser.getDomains()
        );

        userEventProducer.sendUserCreatedEvent(userCreatedEvent);

        return savedUser;
    }


    public String loginUser(LoginRequestDto loginRequestDto){
        User user = userRepository.findByEmail(loginRequestDto.email())
                .orElse(null);

        if(user == null || (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword()))){
            throw new BadCredentialsException("Invalid email or password");
        }

        return jwtService.generateToken(user.getId(), user.getEmail());

    }
}
