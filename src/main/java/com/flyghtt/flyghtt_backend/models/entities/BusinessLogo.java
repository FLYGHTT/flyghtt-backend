package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "business_logos")
public class BusinessLogo {

    @Id
    @Builder.Default private UUID businessLogoId = UUID.randomUUID();

    private String type;

    private UUID businessId;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    private byte[] imageData;
}
