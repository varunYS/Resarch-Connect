package com.researchConnect.recommendation.controller;

import com.researchConnect.recommendation.model.Publication;
import com.researchConnect.recommendation.model.User;
import com.researchConnect.recommendation.repository.PublicationRepository;
import com.researchConnect.recommendation.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("recommendation")
@AllArgsConstructor
public class TestController {
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;

    @GetMapping("/allUsers")
    public List<User> getAllConsumedUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/allPublications")
    public List<Publication> getAllConsumedPublications(){
        return publicationRepository.findAll();
    }
}
