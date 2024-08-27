package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tool_comments")
public class ToolComment {

    @Id
    @Builder.Default private UUID toolCommentId = UUID.randomUUID();

    private UUID toolId;
    private UUID userId;

    private String comment;
    @Builder.Default private Instant createdAt = Instant.now();
    @Builder.Default private Instant updatedAt = Instant.now();
}
