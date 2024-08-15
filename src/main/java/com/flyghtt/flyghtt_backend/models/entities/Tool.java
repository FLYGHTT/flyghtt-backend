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
@Table(name = "tools")
public class Tool {

    @Id
    @Builder.Default
    private UUID toolId = UUID.randomUUID();

    private String name;
    private String description;
    private String link;

    private UUID createdBy;

    @Builder.Default private boolean isPublic = false;
    @Builder.Default private boolean commentable = true;

    @OneToMany(mappedBy = "toolId", cascade = CascadeType.ALL)
    private List<ToolColumn> columns;
}
