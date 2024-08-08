package com.flyghtt.flyghtt_backend.models.response;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AuthenticationResponse {

    private UUID userId;
    private String token;
    private boolean emailVerified;
    private boolean enabled;
}
