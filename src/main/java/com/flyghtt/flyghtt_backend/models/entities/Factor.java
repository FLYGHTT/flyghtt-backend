package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long factorId;

    private String name;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "factor_values", joinColumns = @JoinColumn(name = "factor_id"))
    @Column(name = "value", nullable = false)
    private List<String> values;

    @ManyToOne
    @JoinColumn(name = "business_tool", nullable = false)
    private BusinessTool businessTool;

    private UUID businessId;
}
