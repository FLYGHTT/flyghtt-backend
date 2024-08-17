package com.flyghtt.flyghtt_backend.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ToolRequest {

    @NotNull
    private String toolName;
    @NotNull
    private String toolDescription;
    private String link;
    private boolean commentable;
    private boolean isPublic;

    @NotNull
    private List<ColumnRequest> columns;
}
