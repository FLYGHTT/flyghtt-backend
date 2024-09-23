package com.flyghtt.flyghtt_backend.models.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ToolResponse {

    private UUID toolId;
    private String name;
    private String description;
    private String link;
    private UUID createdBy;
    private boolean commentable;
    private String columns;
}
