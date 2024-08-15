package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class ToolRequest {

    private String toolName;
    private String toolDescription;
    private String link;
    private boolean commentable;
    private boolean isPublic;

    private List<ColumnRequest> columns;
}
