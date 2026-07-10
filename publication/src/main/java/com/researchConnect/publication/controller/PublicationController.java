package com.researchConnect.publication.controller;

import com.researchConnect.publication.dto.CreatePublicationDto;
import com.researchConnect.publication.model.Publication;
import com.researchConnect.publication.model.User;
import com.researchConnect.publication.service.PublicationService;
import com.researchConnect.publication.service.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/publication")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;
    private final S3StorageService s3StorageService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getConsumedUsers() {
        List<User> consumedUser = publicationService.getConsumedUser();
        return ResponseEntity.ok(consumedUser);
    }

    @GetMapping
    public ResponseEntity<List<Publication>> getUserPublications(){
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Publication> userPublications = publicationService.getUserPublications(id);

        return ResponseEntity.ok(userPublications);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Publication> createPublication(
            @RequestPart("metadata") CreatePublicationDto metadata,
            @RequestPart("file") MultipartFile file
    ) {
        String s3Key = s3StorageService.uploadFile(file);
        Publication publication = publicationService.createPublication(metadata, s3Key);

        return new ResponseEntity<>(publication, HttpStatus.CREATED);
    }

}