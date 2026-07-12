package com.researchConnect.user.event;

import java.util.List;

public record UserCreatedEvent(Long userId,
                               String name,
                               String email,
                               String institution,
                               List<String> domains) {}
