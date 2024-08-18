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
@Table(name = "factors")
public class Factor {

    @Id
    @Builder.Default private UUID factorId = UUID.randomUUID();

    private String name;
    private UUID columnId;
}
