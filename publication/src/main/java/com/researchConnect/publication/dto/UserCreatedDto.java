package com.researchConnect.publication.dto;

import java.util.List;

public record UserCreatedDto(Long userId, String name, String email, String institution, List<String> domains) {}
