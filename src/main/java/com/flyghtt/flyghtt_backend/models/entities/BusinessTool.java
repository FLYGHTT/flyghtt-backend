package com.flyghtt.flyghtt_backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "business_tools")
public class BusinessTool {
    @Id
    @Builder.Default
    private UUID businessToolId = UUID.randomUUID();

    private UUID businessId;
    private UUID factorId;

    private String value;
}
