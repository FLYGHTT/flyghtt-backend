package com.flyghtt.flyghtt_backend.models.entities;


import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "tools")
public class Tool {

    @Id
    @Builder.Default private UUID toolId = UUID.randomUUID();

    private String name;
    private String description;
    private String link;
    private UUID createdBy;

    @Builder.Default private boolean commentable = true;
    @Builder.Default private boolean isPublic = true;

    @ManyToMany(mappedBy = "likedTools")
    private Set<User> likedUsers;

    public ToolResponse toDto() {

        return ToolResponse.builder()
                .toolId(toolId)
                .name(name)
                .description(description)
                .link(link)
                .createdBy(createdBy)
                .commentable(commentable)
                .build();
    }

    @Override
    public boolean equals(Object tool) {

        if (getClass() != tool.getClass())
            return false;

        Tool tool2 = (Tool) tool;

        return toolId.equals(tool2.getToolId());
    }
}
