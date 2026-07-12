package com.researchConnect.recommendation.dto;

import java.util.List;

public record UserCreatedDto(Long userId,
                             String name,
                             String email,
                             String institution,
                             List<String> domains
) {}
