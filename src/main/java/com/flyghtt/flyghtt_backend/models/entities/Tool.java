package com.flyghtt.flyghtt_backend.models.entities;

import com.flyghtt.flyghtt_backend.models.requests.ColumnRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
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
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "toolId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ToolColumn> columns;

    public ToolRequest toDto() {

        ToolRequest response = ToolRequest.builder()
                .toolName(name)
                .toolDescription(description)
                .link(link)
                .commentable(commentable)
                .isPublic(isPublic)
                .build();

        response.setColumns(
                columns.stream().map(
                        column -> ColumnRequest.builder()
                                .columnName(column.getName())
                                .description(column.getDescription())
                                .factors(
                                        columns.stream().map(
                                                ToolColumn::getName
                                        ).collect(Collectors.toList())
                                ).build()
                ).collect(Collectors.toList())
        );

        return response;
    }
}
