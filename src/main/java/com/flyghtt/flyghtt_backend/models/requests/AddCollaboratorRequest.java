package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;
import java.util.UUID;

@Data
public class AddCollaboratorRequest {

    private UUID collaboratorId;
    private String role;
}
