package com.flyghtt.flyghtt_backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "columns")
public class Column {

    @Id
    @Builder.Default private UUID columnId = UUID.randomUUID();

    private String name;
    private String description;

    private UUID toolId;
}
