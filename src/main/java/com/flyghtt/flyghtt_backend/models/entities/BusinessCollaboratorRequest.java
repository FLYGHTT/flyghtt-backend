package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "business_collaborator_requests")
public class BusinessCollaboratorRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long businessCollaboratorRequestId;

    private UUID businessId;
    private UUID collaboratorId;
    private String role;

    @Enumerated(EnumType.STRING)
    @Builder.Default private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
}
