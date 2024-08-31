package com.flyghtt.flyghtt_backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "business_collaborators")
public class BusinessCollaborator {

    @Id
    @Builder.Default private UUID businessCollaboratorId = UUID.randomUUID();

    private String role;

    private UUID businessId;
    private UUID userId;
}
