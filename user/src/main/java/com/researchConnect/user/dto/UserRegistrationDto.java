package com.researchConnect.user.dto;

import java.util.List;

public record UserRegistrationDto(
    String name,
    String email,
    String password,
    String institution,
    List<String> domains
) {}
