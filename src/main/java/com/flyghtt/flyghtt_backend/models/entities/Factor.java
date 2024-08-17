package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "factors")
public class Factor {

    @Id
    @Builder.Default private UUID factorId = UUID.randomUUID();

    private String name;
    private UUID columnId;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @CollectionTable(name = "factor_values", joinColumns = @JoinColumn(name = "factor_id"))
    @Column(name = "value")
    private List<String> factorValues;
}
