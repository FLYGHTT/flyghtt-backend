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
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tool_columns")
public class ToolColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long columnId;

    private String name;
    private String description;
    private UUID toolId;

    @OneToMany(mappedBy = "columnId")
    private List<ColumnFactor> columnFactors;
}
