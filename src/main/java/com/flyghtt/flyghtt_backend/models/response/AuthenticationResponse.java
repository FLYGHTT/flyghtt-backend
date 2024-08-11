package com.flyghtt.flyghtt_backend.models.response;


import com.flyghtt.flyghtt_backend.models.entities.Role;
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
    private Role role;
}
