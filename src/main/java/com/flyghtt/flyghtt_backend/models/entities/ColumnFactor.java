package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "column_factors")
public class ColumnFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long columnFactorId;

    private String factorName;
    private String factorDescription;
    private long columnId;

    @OneToMany(mappedBy = "columnFactorId")
    private List<FactorValue> factorValues;
}
