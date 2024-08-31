package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class CollaboratorIdRequest {

    private Set<UUID> collaboratorIds;
}
