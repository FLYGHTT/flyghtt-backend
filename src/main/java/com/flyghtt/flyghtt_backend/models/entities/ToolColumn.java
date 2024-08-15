package com.flyghtt.flyghtt_backend.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "tool_columns")
public class ToolColumn {

    @Id
    @Builder.Default
    private UUID columnId = UUID.randomUUID();

    private String name;
    private String description;
    private UUID toolId;

    @OneToMany(mappedBy = "columnId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ColumnFactor> columnFactors;
}
