package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tools")
public class Tool {

    @Id
    @Builder.Default
    private UUID toolId = UUID.randomUUID();

    private String name;
    private String description;
    private boolean isPublic;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "tool_factors", joinColumns = @JoinColumn(name = "tool_id", referencedColumnName = "toolId"))
//    @MapKeyColumn(name = "column")
//    @Column(name = "factor")
//    private Map<Integer, String> columnToFactorsMap;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "factor_column_id")
    List<FactorColumn> factorColumns;

    private UUID createdBy;
}
