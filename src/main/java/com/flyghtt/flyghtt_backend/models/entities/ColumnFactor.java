package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "column_factors")
public class ColumnFactor {

    @Id
    @Builder.Default
    private UUID columnFactorId = UUID.randomUUID();

    private String factorName;
    private UUID columnId;

    @OneToMany(mappedBy = "columnFactorId", cascade = CascadeType.ALL)
    private List<FactorValue> factorValues;
}
