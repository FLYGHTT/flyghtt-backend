package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "factor_values")
public class FactorValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long factorValueId;

    private UUID businessId;
    private UUID columnFactorId;

    private String factorValue;
}
