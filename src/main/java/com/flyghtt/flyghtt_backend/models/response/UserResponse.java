package com.flyghtt.flyghtt_backend.models.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
}
